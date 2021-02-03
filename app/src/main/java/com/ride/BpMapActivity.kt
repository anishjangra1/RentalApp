package com.ride

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BpMapActivity : AppCompatActivity(), OnMapReadyCallback, SubscribeFragment.ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    // [END maps_marker_get_map_async]
    // [END_EXCLUDE]
    // [START_EXCLUDE silent]
    override fun onMapReady(googleMap: GoogleMap) {
        val location1 = LatLng(28.4649909, 77.0318711)
        val location2 = LatLng(28.4649909, 77.0318711)
        val location3 = LatLng(28.4649909, 77.0318711)
        googleMap.addMarker(
            MarkerOptions()
                .position(location1)

        )
        // [START_EXCLUDE silent]
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location1))
//        googleMap.setOnMarkerClickListener { clickListener }
        googleMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
//            val position = marker.tag as Int
            //Using position get Value from arraylist
            supportFragmentManager.let {
                SubscribeFragment.newInstance(Bundle()).apply {
                    show(it, tag)
                }
            }
            false
        })

    }

    override fun onItemClick(item: String) {
        Toast.makeText(this,item,Toast.LENGTH_LONG).show()

    }

}