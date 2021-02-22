package com.ride.home.booking

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ride.R
import com.ride.adapters.ItemListener
import com.ride.adapters.RidePlanAdapter
import com.ride.databinding.FragmentPlansBinding
import com.ride.home.MainViewModel
import com.ride.home.booking.ride.HomeFragment
import com.ride.utils.SpaceItemDecoration
import com.truefan.user.helper.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class PlansFragment : Fragment() {

    private lateinit var rideAdapter: RidePlanAdapter
    private lateinit var binding: FragmentPlansBinding
    private var param1: String? = null
    private var param2: String? = null

    private val  viewModel: GetPlanViewModel by viewModels()
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

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
        binding = FragmentPlansBinding.inflate(layoutInflater, container, false).apply {
            getPlansViewModel = viewModel
            loadingSpinner.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.showBottomNavigation(false)
        // Get ride plans
        viewModel.getPlans()

        // Observe plans
        viewModel.planResponseList.observe(viewLifecycleOwner){
            binding.loadingSpinner.visibility = View.GONE
            rideAdapter = RidePlanAdapter(it, ItemListener{ plan ->
                // On Plan selected
                viewModel.selectPlan(plan)
                log("item clicked -> selected plan is ${plan.id}")
            })
//            binding.rvPlans.addItemDecoration(
//                GridSpacingItemDecoration(1, 30, true, 0)
//            )
            binding.rvPlans.addItemDecoration(SpaceItemDecoration(15))
            binding.rvPlans.adapter = rideAdapter
        }

        viewModel.changePlan.observe(viewLifecycleOwner){
            rideAdapter.notifyDataSetChanged()
        }

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.startRide.observe(viewLifecycleOwner){
            when (it) {
                true -> {
                    binding.loadingSpinner.visibility = View.VISIBLE
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        Toast.makeText(context, "Payment successfully done!", Toast.LENGTH_SHORT)
//                            .show()
//                        binding.loadingSpinner.visibility = View.GONE
//                        binding.root.findNavController()
//                            .navigate(R.id.action_planFragment_to_startRideFragment)
//                        viewModel.removeObserver()
//                    }, 2000)
                }
            }
        }
    }


    companion object {

        private const val TAG = "PlansFragment:"

        @JvmStatic
        fun log(printLine: String){
            println("RideApplication: $TAG : $printLine")
        }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlansFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}