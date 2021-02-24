package com.ride.payment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private var vehicleLng: Float? = null
    private var vehicleLat: Float? = null
    private var vehicleId: Int? = null

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel

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
                    binding.progressBar.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.progressBar.visibility = View.GONE
                        val action = PaymentFragmentDirections.actionPaymentFragmentToRideStartedFragment(
                            vehicleId!!,
                            vehicleLat!!,
                            vehicleLng!!
                        )
                        binding.root.findNavController()
                            .navigate(action)

                    }, 1000)
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