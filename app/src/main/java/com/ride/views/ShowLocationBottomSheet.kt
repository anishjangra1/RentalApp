package com.ride.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ride.adaptor.MapListAdapter
import com.ride.adaptor.VehicleItemListener
import com.ride.data.Vehicle
import com.ride.databinding.BottomSheetShowLocationsBinding
import java.lang.reflect.Type


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

        mapAdapter = MapListAdapter(VehicleItemListener {
            // Navigate
        })

        binding.rvLocations.adapter = mapAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!data.isNullOrBlank()){
            val listOfMyClassObject: Type = object : TypeToken<ArrayList<Vehicle>>() {}.type
            val list: List<Vehicle> = Gson().fromJson(data, listOfMyClassObject)
            mapAdapter.submitData(list)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
        val resources = resources
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            assert(view != null)
            val parent = view?.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.setMargins(
                0, // LEFT 16dp
                0,
                0,
                0,
            )
            parent.layoutParams = layoutParams
        }

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