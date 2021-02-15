package com.ride.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ride.adapters.ItemListener
import com.ride.adapters.RidePlanAdapter
import com.ride.databinding.FragmentPlansBinding
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
        loadingSpinner.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get ride plans
        viewModel.getPlans()

        // Observe plans
        viewModel.planResponseList.observe(viewLifecycleOwner){
            binding.loadingSpinner.visibility = View.GONE
            rideAdapter = RidePlanAdapter(it, ItemListener{ plan ->
                // On Plan selected
                viewModel.selectPlan(plan)
                println("item clicked -> selected plan is ${plan.id}")
            })
            binding.rvPlans.addItemDecoration(
                GridSpacingItemDecoration(1, 30, true, 0)
            )
            binding.rvPlans.adapter = rideAdapter
        }

        viewModel.changePlan.observe(viewLifecycleOwner){
            rideAdapter.notifyDataSetChanged()
        }

    }

    companion object {

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