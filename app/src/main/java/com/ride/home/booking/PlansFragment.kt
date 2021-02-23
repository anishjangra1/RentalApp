package com.ride.home.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ride.R
import com.ride.adapters.ItemListener
import com.ride.adapters.PlanAdapter
import com.ride.databinding.FragmentPlansBinding
import com.ride.home.MainViewModel
import com.ride.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class PlansFragment : Fragment() {

    private lateinit var adapter: PlanAdapter
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
            toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener{
                requireActivity().onBackPressed()
            }
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
            adapter = PlanAdapter(it, ItemListener{ plan ->
                // On Plan selected
                plan.let {
                    binding.root.findNavController().navigate(
                        PlansFragmentDirections.actionPlanFragmentToPaymentFragment(
                            plan.id!!,
                            plan.plan!!,
                            plan.rate!!
                        )
                    )
//                    binding.root.findNavController().navigate(R.id.action_planFragment_to_paymentFragment)
                }

            })
//            binding.rvPlans.addItemDecoration(
//                GridSpacingItemDecoration(1, 30, true, 0)
//            )
            binding.rvPlans.addItemDecoration(SpaceItemDecoration(15))
            binding.rvPlans.adapter = adapter
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