package com.ride.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.ride.BPFragment
import com.ride.GetPlansActivity
import com.ride.R
import com.ride.data.Vehicle
import com.ride.databinding.ActivityMaps2Binding
import com.ride.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, BPFragment.ItemClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMaps2Binding

    val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.availableVehicles.observe(this){
            val data = Gson().toJson(it)
//            ShowLocationBottomSheet.newInstance(data).show(supportFragmentManager, "Dialog Fragment")
            showMarkers(it)
        }

        viewModel.getNearbyVehicles()
    }

    private fun showMarkers( list: List<Vehicle>?) {

        if (list != null) {
            for (element in list) {
                addMarker(element)
            }
            val item : Vehicle
            item=list.get(0)

            val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13F), 2000, null);
            mMap.setOnMarkerClickListener(OnMarkerClickListener { Marker ->
//            val position = marker.tag as Int
                supportFragmentManager.let {
                    BPFragment.newInstance(Bundle()).apply {
                        show(it, tag)
                    }
                }
                false
            })
        }
    }

    private fun addMarker(item: Vehicle) {

        val location = LatLng(item.latitude!!.toDouble(), item.longitude!!.toDouble())
        mMap.addMarker(MarkerOptions().position(location).title(item.name))

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        mMap.setOnMarkerClickListener(OnMarkerClickListener { Marker ->
////            val position = marker.tag as Int
//            supportFragmentManager.let {
//                BPFragment.newInstance(Bundle()).apply {
//                    show(it, tag)
//                }
//            }
//            false
//        })
    }

    override fun onItemClick(item: String) {
        startActivity(Intent(this, GetPlansActivity::class.java))
    }
}