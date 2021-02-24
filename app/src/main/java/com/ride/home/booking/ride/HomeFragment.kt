package com.ride.home.booking.ride

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.ride.R
import com.ride.adapters.MapListAdapter
import com.ride.adapters.VehicleItemListener
import com.ride.data.Vehicle
import com.ride.databinding.FragmentHomeBinding
import com.ride.utils.PermissionUtils
import com.ride.utils.PermissionUtils.isPermissionGranted
import com.ride.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener{


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private var hasCurrentLocation: Boolean = false
    private var lastKnownLocation: Location? = null
    private var permissionDenied: Boolean? = false
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentHomeBinding

    val viewModel: HomeViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        log("map is ready !")
        map = googleMap
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableMyLocation()
    }


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false).apply {
            fusedLocationClient =  LocationServices.getFusedLocationProviderClient(requireActivity())
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            getMyLocation()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNearbyVehicles()
        // set action bar title
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetView).apply {
            addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // TODO "Not yet implemented"
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                   // TODO "Not yet implemented"
                }
            })
        }

        setObservers()
    }

    private fun setObservers() {
        viewModel.availableVehicles.observe(viewLifecycleOwner){
            val data = Gson().toJson(it)
            showMarkers(it)
            binding.rvLocations.apply {
                adapter = MapListAdapter(
                VehicleItemListener { vehicle ->
                    run {
                        println("RideStartedFragment: arguments:  id = ${vehicle.id} lat =  ${vehicle.latitude}  lng = ${vehicle.longitude}")
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToPlansFragment(
                                vehicle.id!!,
                                vehicle.latitude!!,
                                vehicle.longitude!!
                            )
                        binding.root.findNavController().navigate(action)
                    }
                },
                it)
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.divider)?.apply {
                            setDrawable(this) }
                    }
                )
            }
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun showMarkers( list: List<Vehicle>?) {
        if (list != null) {
            for (element in list) {
                addMarker(element)
            }
            map.setOnInfoWindowClickListener {
                val vehicle = viewModel.getVehicleById(it.tag as Int)
                vehicle.let { value ->
                    run {
                        val action = HomeFragmentDirections.actionHomeFragmentToPlansFragment(
                            value.id!!,
                            value.latitude!!,
                            value.longitude!!
                        )
                        binding.root.findNavController().navigate(action)
                    }
                }
            }
        }

    }

    private fun addMarker(item: Vehicle) {
        val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
        val marker = map.addMarker(
            MarkerOptions()
                .position(location)
                .title(item.name)
//                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
        )

        // set id as a tag in marker
        marker.tag  = item.id
    }
//    If we want to set vector drawables  as marker
//    private fun bitmapDescriptorFromVector(
//        context: Context,
//        @DrawableRes vectorDrawableResourceId: Int
//    ): BitmapDescriptor? {
////        val background = ContextCompat.getDrawable(context, R.drawable.ic_pin)
////        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
//        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
//        vectorDrawable!!.setBounds(
//            0,
//            0,
//            vectorDrawable.intrinsicWidth,
//            vectorDrawable.intrinsicHeight
//        )
//        val bitmap = Bitmap.createBitmap(
//            vectorDrawable.intrinsicWidth,
//            vectorDrawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
////        background.draw(canvas)
//        vectorDrawable.draw(canvas)
//        return BitmapDescriptorFactory.fromBitmap(bitmap)
//    }

    private fun createLocationRequest(){
        val task  = PermissionUtils.getLocationRequest(requireContext())
        task.addOnSuccessListener {
            log("LocationSettings response is success")
            // Location is on set map's camera to current Location
            if(!hasCurrentLocation)
                getMyLocation()
        }
        task.addOnFailureListener{
            if(it is ResolvableApiException){
                // Location settings are not satisfied prompt a dialog to user to change location
                // settings
                    log("LocationSettingsResponse is failed! prompt dialog")
                startIntentSenderForResult(it.resolution.intentSender, REQUEST_CHANGE_LOCATION_SETTINGS, null, 0, 0, 0, null)
//                it.startResolutionForResult(requireActivity(), REQUEST_CHANGE_LOCATION_SETTINGS)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // Get and set my current location
        fusedLocationClient.lastLocation.addOnCompleteListener{
                task -> if (task.isSuccessful) {
            // Set the map's camera position to the current location of the device.
                log("getMyLocation() task completed!")
            lastKnownLocation = task.result
            if (lastKnownLocation != null) {
                log("latitude ${lastKnownLocation!!.latitude} and  longitude ${lastKnownLocation!!.longitude}")
                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
            }
            else {
                log("lastKnownLocation is null")
            }
        } else {
            log("Current location is null. Using defaults.")
            log("Exception: %s $task.exception")

        }
        }

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
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)

//          Use below when initializing map with Activity
//                requestPermission(
//                requireActivity() as AppCompatActivity, LOCATION_PERMISSION_REQUEST,
//                Manifest.permission.ACCESS_FINE_LOCATION, true
//            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        log("onRequestPermissionsResult() called!")
        if (requestCode != LOCATION_PERMISSION_REQUEST) {
            return
        }

        // PermissionUtils.isPermissionGranted
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
                log("Permission granted")
            enableMyLocation()

        } else {
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }
    }

    override fun onMyLocationClick(p0: Location) {

    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        log("onActivityResult is called")
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CHANGE_LOCATION_SETTINGS){
            log("request code is REQUEST_CHANGE_LOCATION_SETTINGS")
            createLocationRequest()
        }
    }
    companion object {
        const val  LOCATION_PERMISSION_REQUEST = 0x23
        const val  REQUEST_CHANGE_LOCATION_SETTINGS = 0x25
        const val TAG = "RideStartedFragment:"
        const val DEFAULT_ZOOM = 9

        @JvmStatic
        fun newInstance(bundle: Bundle): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }

        @JvmStatic
        fun log(printLine: String){
           println("RideApplication: $TAG : $printLine")
        }
    }
}