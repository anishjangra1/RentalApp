package com.ride.home.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.ride.R
import com.ride.data.Vehicle
import com.ride.databinding.FragmentRideBinding
import com.ride.home.MainViewModel
import com.ride.utils.Constant
import com.ride.utils.GpsUtils
import com.ride.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class RideFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentRideBinding? = null

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val binding get() = _binding!!
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private val isContinue = false
    private var isGPS = false
    private var locationCallback: LocationCallback? = null
    private var wayLatitude = 0.0
    private var wayLongitude = 0.0
    private lateinit var addresses: List<Address>
    private var locality: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRideBinding.inflate(inflater, container, false)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.map_key), Locale.US);
        }

        viewModel.getNearbyVehicles()
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.availableVehicles.observe(viewLifecycleOwner) {
//            val data = Gson().toJson(it)
//            ShowLocationBottomSheet.newInstance(data).show(supportFragmentManager, "Dialog Fragment")
            showMarkers(it)
        }

        initializeMapAndLocation()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.showBottomNavigation(true)
    }

    private fun showMarkers(list: List<Vehicle>?) {
        list?.let {
            list.forEach { value -> addMarker(value) }
            setMapCamera(list[0])
        }
//            mMap.setOnMarkerClickListener(OnMarkerClickListener { Marker ->
////            val position = marker.tag as Int
//                supportFragmentManager.let {
//                    BPFragment.newInstance(Bundle()).apply {
//                        show(it, tag)
//                    }
//                }
//                false
//            })
    }


    private fun setMapCamera(vehicle: Vehicle){
        val location = LatLng(vehicle.latitude!!.toDouble(), vehicle.longitude!!.toDouble())
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 1 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10F), 500, null);
    }

    private fun initializeMapAndLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 10 * 1000.toLong()

        locationRequest?.fastestInterval = 5 * 1000.toLong()


        GpsUtils(requireActivity())
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
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
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
                mFusedLocationClient?.lastLocation?.addOnSuccessListener(requireActivity()) { location ->
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



    private fun getAddress(latitude: Double, longitude: Double) {
        try {
            val geo = Geocoder(requireActivity(), Locale.getDefault())
            addresses = geo.getFromLocation(latitude, longitude, 1)
            val address = addresses[0]
            locality = address.locality

        } catch (exception: IOException) {

        }
    }

    private fun addMarker(item: Vehicle) {
        val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(item.name)
                .snippet(item.vehicleNo)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener {
//            binding.root.findNavController().navigate(R.id.action_home)
//            Toast.makeText(requireContext(), "Window info clicked", Toast.LENGTH_LONG).show()
        }
    }
}