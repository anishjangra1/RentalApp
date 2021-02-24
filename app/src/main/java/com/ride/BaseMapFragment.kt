package com.ride

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.ride.home.booking.ride.HomeFragment
import com.ride.utils.PermissionUtils

abstract class BaseMapFragment : Fragment(),
    GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener
{

    companion object{
        private const val  TAG = "BaseMapFragment: "
        const val  LOCATION_PERMISSION_REQUEST = 0x25
        const val  REQUEST_CHANGE_LOCATION_SETTINGS = 0x27
        const val DEFAULT_ZOOM = 9

        fun log(message: String){
            println("$TAG $message")
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    protected lateinit var map: GoogleMap
    private var permissionDenied: Boolean?= false
    private var hasCurrentLocation: Boolean = false

    private val callback = OnMapReadyCallback { googleMap ->
        log("map is ready !")
        map = googleMap
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableMyLocation()
    }

    abstract fun getMapFragment(): SupportMapFragment
    abstract fun onCurrentLocationFound(lat: Double, lng: Double)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapFragment().getMapAsync(callback)
        getMyLocation()
    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private fun enableMyLocation() {
        log("enableMyLocation() called!")
        if (!::map.isInitialized) {
            log("map is not initialized")
            return
        }

        // check if permission is granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (map != null) {
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = true
                createLocationRequest()
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            log("Requesting permissions")
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        }
    }

    // Check if application has location request or not
    // If not create one and get location request access by showing enable location dialog
    private fun createLocationRequest(){
        val task  = PermissionUtils.getLocationRequest(requireContext())
        task.addOnSuccessListener {
            HomeFragment.log("LocationSettings response is success")
            // Location is on set map's camera to current Location
            getMyLocation()
        }
        task.addOnFailureListener{
            if(it is ResolvableApiException){
                // Location settings are not satisfied, prompt a dialog to user to change location
                // settings
                log("LocationSettingsResponse is failed! prompt dialog")
                startIntentSenderForResult(it.resolution.intentSender,
                    REQUEST_CHANGE_LOCATION_SETTINGS, null, 0, 0, 0, null)
//                it.startResolutionForResult(requireActivity(), REQUEST_CHANGE_LOCATION_SETTINGS)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // Get and set my current location
        fusedLocationClient?.lastLocation!!.addOnCompleteListener{
                task -> if (task.isSuccessful) {
            // Set the map's camera position to the current location of the device.
            log("getMyLocation() task completed!")
            lastKnownLocation = task.result
            if (lastKnownLocation != null) {
                log("latitude ${lastKnownLocation!!.latitude} and  longitude ${lastKnownLocation!!.longitude}")
                map?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                    LatLng(lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude), HomeFragment.DEFAULT_ZOOM.toFloat()))

                onCurrentLocationFound(
                    lastKnownLocation!!.latitude
                    , lastKnownLocation!!.longitude
                )
            }
            else {
                // retry to fetch location
                log("lastKnownLocation is null")
                Handler(Looper.getMainLooper()).postDelayed({
                    getMyLocation()
                }, 500)
            }
        } else {
            log("Current location is null. Using defaults.")
            log("Exception: %s $task.exception")

        }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        HomeFragment.log("onRequestPermissionsResult() called!")
        if (requestCode != HomeFragment.LOCATION_PERMISSION_REQUEST) {
            return
        }

        // PermissionUtils.isPermissionGranted
        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            HomeFragment.log("Permission granted")
            enableMyLocation()

        } else {
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }
    }



    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onMyLocationClick(p0: Location) {

    }
}