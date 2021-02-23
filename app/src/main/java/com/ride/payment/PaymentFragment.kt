package com.ride.payment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import com.ride.R
import com.ride.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false).apply {
            toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener{
                requireActivity().onBackPressed()
            }

            containerView.also {
                it.planName.text = requireArguments().getString("planName")
                it.duration.text = requireArguments().getFloat("amount").toString()
                it.tvPrice.text = requireArguments().getFloat("amount").toString()
                it.tvProceed.setOnClickListener {
                    binding.root.findNavController()
                        .navigate(R.id.action_paymentFragment_to_startRideFragment)
                }
            }

        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

    }

}