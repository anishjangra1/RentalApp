package com.ride.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ride.adapters.MapListAdapter
import com.ride.databinding.BottomSheetShowLocationsBinding

class ShowLocationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var mapAdapter: MapListAdapter
    private var data: String?="Dialog Fragment"

    private lateinit var binding: BottomSheetShowLocationsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = requireArguments().getString(KEY_DATA)
        println("onCreate() -> $data")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            BottomSheetShowLocationsBinding.inflate(layoutInflater, container, false)

//        mapAdapter = MapListAdapter(VehicleItemListener {
//            // Navigate
//        })

//        binding.rvLocations.adapter = mapAdapter
//
//        if(!data.isNullOrBlank()){
//            val list = Gson().fromJson<List<Vehicle>>(data, List::class.java)
//            mapAdapter.submitData(list)
//        }
        return binding.root
    }

    companion object {
        const val KEY_DATA = "data"

        @JvmStatic
        fun newInstance(data: String): ShowLocationBottomSheet = ShowLocationBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(KEY_DATA, data)
                }
        }
    }
}