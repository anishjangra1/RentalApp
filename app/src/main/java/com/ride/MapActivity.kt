package com.ride

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.observe
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.gson.Gson
import com.ride.adaptor.MapListAdapter
import com.ride.adaptor.VehicleItemListener
import com.ride.data.Vehicle
import com.ride.databinding.ActivityMapBinding
import com.ride.utils.Constant
import com.ride.utils.GpsUtils
import com.ride.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback , BPFragment.ItemClickListener{
    private var mapLoaded: Boolean = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var binding: ActivityMapBinding
    private var ivBack: ImageView? = null;
    private lateinit var mMap: GoogleMap
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var addresses: List<Address>
    private var locality: String = ""
    private var locationRequest: LocationRequest? = null
    private var isGPS = false
    private var locationCallback: LocationCallback? = null
    private var wayLatitude = 0.0
    private var wayLongitude = 0.0
    private val isContinue = false

    val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = intent.extras

        println("MapsActivity: ${arguments!!.getInt("user_id")}")
        println("MapsActivity: ${arguments!!.getString("mobile_number")}")

        binding = ActivityMapBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        bottomSheetBehavior = from(binding.bottomSheetView).apply {
            addBottomSheetCallback( object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        STATE_EXPANDED -> { // move map
                            if(mapLoaded) {
//                                mMap.animateCamera(CameraUpdateFactory.scrollBy(0.0f, 400f))
                            }
                        }
                        STATE_COLLAPSED -> { // TODO: 13/02/21 move back map to initial place }
                            if(mapLoaded) {
//                                mMap.animateCamera(CameraUpdateFactory.scrollBy(0.0f, -400f))
                            }
                        }

                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    println("slideOffset: $slideOffset")
                }

            })
        }

//        if (!Places.isInitialized()) {
//            Places.initialize(this@MapActivity, getString(R.string.map_key), Locale.US);
//        }
        initUI()
    }

    private fun initUI() {
//        binding = ActivityMaps2Binding.inflate(layoutInflater)
//        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.map_key), Locale.US);
        }
        viewModel.availableVehicles.observe(this){
            val data = Gson().toJson(it)
            showMarkers(it)
            binding.rvLocations.adapter = MapListAdapter(
                VehicleItemListener {
                },
                it)
            bottomSheetBehavior.state = STATE_EXPANDED
//            ShowLocationBottomSheet.newInstance(data).show(supportFragmentManager, "Dialog Fragment")
//            showMarkers(it)
        }

        viewModel.getNearbyVehicles()


        initializeMapAndLocation()
    }
    private fun showMarkers( list: List<Vehicle>?) {

        if (list != null) {
            for (element in list) {
                addMarker(element)
            }
            val item : Vehicle = list[0]
            val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13F), 2000, null);
            mMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { Marker ->
//            val position = marker.tag as Int
                supportFragmentManager.let {
                    BPFragment.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
                false
            })
        }

        mapLoaded = true
    }

    private fun addMarker(item: Vehicle) {

        val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
        mMap.addMarker(MarkerOptions().position(location).title(item.name))

    }

    private fun initializeMapAndLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 10 * 1000.toLong()

        locationRequest?.fastestInterval = 5 * 1000.toLong()


        GpsUtils(this)
            .turnGPSOn(object : GpsUtils.onGpsListener {
                override fun gpsStatus(isGPSEnable: Boolean) {
                    isGPS = isGPSEnable
                }
            })

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude

                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient?.removeLocationUpdates(locationCallback)
                        }
                    }
                }
            }
        }

        getLocation()
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@MapActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MapActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MapActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constant.LOCATION_REQUEST
            )
        } else {
            if (isContinue) {
                mFusedLocationClient!!.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            } else {
                mFusedLocationClient?.lastLocation?.addOnSuccessListener(this) { location ->
                    if (location != null) {
                        wayLatitude = location.latitude
                        wayLongitude = location.longitude

                        getAddress(wayLatitude, wayLongitude)
                    } else {
                        mFusedLocationClient!!.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            null
                        )
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnInfoWindowClickListener { marker ->
            val latLon: LatLng = marker.position
            var position = getMarkerIndex(marker.id)
        }

        mMap.setOnMapLoadedCallback {
            println("Map: Loaded completely!")
        }
    }

    private fun getMarkerIndex(index: String): Int {
        var myIndex = -1
        try {
            myIndex = index.replace("m", "").toInt()
        } catch (nfe: NumberFormatException) {

        }
        return myIndex
    }


    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.ivBack -> {
                onBackPressed()
            }

        }
    }

//    private val apiService by lazy {
//        ApiService.create()
//    }

//    private fun getHospitalByCity() {
//        if (!Utils.isConnected(this)) {
//            Utils.showAlert(this)
//            return
//        }
//        var searchStr: String
//        if (TextUtils.isEmpty(locality)) {
//            searchStr = "India";
//        } else {
//            searchStr = locality;
//        }
//
//        disposable = apiService.searchHospital(searchStr, "", "")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result ->
//                    if (result.statusCode == 200 && result.status.equals("Success")) {
//                        Log.d("sa", result.data.toString())
//                        hospitalData = result.data!!
//                        displayPinOnMap(result.data)
//                    } else {
//                        val snackBar =
//                            Snackbar.make(main_container, "" + result.message, Snackbar.LENGTH_LONG)
//                        snackBar.view.setBackgroundColor(Color.parseColor("#0000ff"))
//                        snackBar.show()
//                    }
//                },
//                { error ->
//                    val snackBar = Snackbar.make(
//                        main_container, error.message.toString(),
//                        Snackbar.LENGTH_LONG
//                    )
//                    snackBar.view.setBackgroundColor(Color.parseColor("#ff0000"))
//                    snackBar.show()
//                }
//            )
//    }

//    private fun displayPinOnMap(data: ArrayList<HospitalData.Data>?) {
//        mMap.clear();
//        mMap.uiSettings.isMyLocationButtonEnabled = true;
//        mMap.isMyLocationEnabled = true;
//        mMap.uiSettings.isZoomControlsEnabled = true;
//
//
//
//        val builder: Builder = LatLngBounds.builder()
//        for (i in 0 until data!!.size) {
//            Log.e("NUMBER", data[i].hospitalName)
//
//            wayLatitude = data[i].address!!.latitude!!.toDouble()
//            wayLongitude = data[i].address!!.longitude!!.toDouble()
//
//            val latLng = LatLng(wayLatitude, wayLongitude)
//            builder.include(latLng)
//
//            mMap.addMarker(MarkerOptions().position(latLng).title(data[i].hospitalName))
//                .showInfoWindow()
//        }
//
//        if (data != null && data.size == 1) {
//            val latLng = LatLng(
//                data[0].address!!.latitude!!.toDouble(),
//                data[0].address!!.longitude!!.toDouble()
//            )
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8.0f))
//        } else {
//            val treeObserver: ViewTreeObserver = mainContainer.viewTreeObserver
//            treeObserver.addOnGlobalLayoutListener(object :
//                ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    if (mMap != null) {
//                        mMap.moveCamera(
//                            CameraUpdateFactory.newLatLngBounds(
//                                builder.build(),
//                                findViewById<View>(R.id.map).width,
//                                findViewById<View>(R.id.map).height,
//                                80
//                            )
//                        )
//                        mainContainer.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                    }
//                }
//            })
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 3) {
//            when (resultCode) {
//                RESULT_OK -> {
//                    val place = Autocomplete.getPlaceFromIntent(data!!)
//                    tvSearch.setText(place.name.toString())
//                    locality = place.name.toString()
//                    Log.i(TAG, "Place: " + place.name.toString() + ", " + place.id)
//                }
//                AutocompleteActivity.RESULT_ERROR -> {
//                    val status: Status = Autocomplete.getStatusFromIntent(data!!)
//                    Log.i(TAG, status.statusMessage)
//                }
//                RESULT_CANCELED -> {
//                }
//            }
//        }
//        if (requestCode == Constant.GPS_REQUEST) {
//            isGPS = true
//            llProgressBar.visibility = View.VISIBLE
//            Handler().postDelayed({
//                llProgressBar.visibility = View.GONE
//                getLocation()
//            }, 3000)
//        }
//    }

    /*@SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    if (!Utils.isConnected(this)) {
                        Utils.showAlert(this)

                    } else {
                        getAddress(mLastLocation!!.latitude, mLastLocation!!.longitude)
                    }

                    *//* val sydney = LatLng(mLastLocation!!.latitude, mLastLocation!!.longitude)
                     mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f))*//*

                    //mLatitudeText!!.setText(mLatitudeLabel + ":   " + (mLastLocation)!!.latitude)
                    //mLongitudeText!!.setText(mLongitudeLabel + ":   " + (mLastLocation)!!.longitude)
                } else {
                    Toast.makeText(
                        this@MapActivity,
                        getString(R.string.noLocationDetected),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
    }*/

    private fun getAddress(latitude: Double, longitude: Double) {
        try {
            var geo = Geocoder(this@MapActivity, Locale.getDefault())
            addresses = geo.getFromLocation(latitude, longitude, 1)
            var address = addresses[0]
            locality = address.locality

        } catch (exception: IOException) {

        }
    }

    override fun onBackPressed() {

    }

    override fun onPause() {
        super.onPause()

    }

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constant.LOCATION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isContinue) {
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return
                        }
                        mFusedLocationClient!!.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            null
                        )
                    } else {
                        mFusedLocationClient?.lastLocation?.addOnSuccessListener(this) { location ->
                            if (location != null) {
                                wayLatitude = location.latitude
                                wayLongitude = location.longitude
                                getAddress(wayLatitude, wayLongitude)
                            } else {
                                mFusedLocationClient!!.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    null
                                )
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClick(item: String) {
        startActivity(Intent(this, GetPlansActivity::class.java))
        finish()
    }
}