package com.ride.home.booking.ride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ride.BaseMapFragment
import com.ride.R
import com.ride.databinding.FragmentRideStartedBinding
import com.ride.utils.SpaceItemDecoration

class RideStartedFragment : BaseMapFragment() {

    companion object {
        fun newInstance() = RideStartedFragment()
    }

    private var vehicleLng: Float? = null
    private var vehicleLat: Float? = null
    private var vehicleId: Int? = null

    private lateinit var binding: FragmentRideStartedBinding
    private lateinit var viewModel: RideStartedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arguments?.let {
                vehicleId = it.getInt(getString(R.string.vehicle_id))
                vehicleLat = it.getFloat(getString(R.string.vehicle_lat))
                vehicleLng = it.getFloat(getString(R.string.vehicle_lng))
            }
            println("RideStartedFragment: arguments: $vehicleId $vehicleLat $vehicleLng")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRideStartedBinding.inflate(layoutInflater, container, false).apply{
            completeTrip.setOnClickListener{
                requireActivity().onBackPressed()
            }
        }

        binding.rvVehiclesSpecification.addItemDecoration(SpaceItemDecoration(18))
        return binding.root
    }

    override fun onCurrentLocationFound(lat: Double, lng: Double) {
        log("onCurrentLocationFound()")
        val location = LatLng(vehicleLat!!.toDouble(), vehicleLng!!.toDouble())
        val marker = map.addMarker(
            MarkerOptions()
                .position(location)
                .title("")
//                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvVehiclesSpecification.adapter =
            VehicleSpecAdapter()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RideStartedViewModel::class.java)

    }

    override fun getMapFragment(): SupportMapFragment {
        return childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    }
}