package com.ride.home.booking.ride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ride.R
import com.ride.databinding.FragmentRideStartBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StartRideFragment: Fragment() {

    private lateinit var binding:FragmentRideStartBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentRideStartBinding.inflate(layoutInflater, container, false).apply {
            startRide.setOnClickListener{
                requireActivity().onBackPressed()
            }
            toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
                toolbar.setNavigationOnClickListener{
                    requireActivity().onBackPressed()
                }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartRideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}