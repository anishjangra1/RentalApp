package com.ride.login

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.ride.R
import com.ride.databinding.FragmentLoginBinding
import com.ride.utility.AlertUtil
import com.ride.viewmodels.GenerateOtpViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val  MOBILE_LENGTH = "10"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var btnGenerateOtp: TextView
    private lateinit var etEnterOtp: EditText
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var etPhoneNumber: EditText
    private lateinit var binding: FragmentLoginBinding
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: GenerateOtpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentLoginBinding.inflate(inflater, container, false).apply {
            etPhoneNumber!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if(s?.length.toString() == MOBILE_LENGTH){
                        btnGenerateOtp?.setBackgroundResource(R.drawable.bg_rounded_corners_with_grey_stroke)
                        btnGenerateOtp!!.setTextColor(Color.parseColor("#000000"));
                    }else{
                        btnGenerateOtp?.setBackgroundResource(R.drawable.button_rounded_inactive)
                        btnGenerateOtp!!.setTextColor(Color.parseColor("#ffffff"));
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            privacyLayout.setOnClickListener(clickListener)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etPhoneNumber = binding.etPhoneNumber
        loadingIndicator = binding.loadingSpinner
        etEnterOtp = binding.etEnterOtp
        btnGenerateOtp = binding.btnGenerateOtp.apply {
            this.setOnClickListener(clickListener)
        }

        registerObserver()
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnGenerateOtp -> {
                generateOtp()
            }
        }
    }

    private fun generateOtp(){
        when(btnGenerateOtp.text){
            GENERATE_OTP -> {
                loadingIndicator.visibility = View.VISIBLE
                viewModel.generateOtp(etPhoneNumber.text.toString())
            }
            LOGIN -> {
                loadingIndicator.visibility = View.VISIBLE
                viewModel.validateOtp(etEnterOtp.text.toString())
            }
        }
    }

    private fun registerObserver(){
        viewModel.verifyOtp.observe(viewLifecycleOwner){
            loadingIndicator.visibility = View.GONE
            AlertUtil.showToastShort(requireContext(), "Please enter Otp")
            etEnterOtp.visibility = View.VISIBLE
            btnGenerateOtp.text = LOGIN
        }

        viewModel.notValidNumber.observe(viewLifecycleOwner){
            loadingIndicator.visibility = View.GONE
            AlertUtil.showToastShort(requireContext(), "Please enter valid mobile number")
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner){
            loadingIndicator.visibility = View.GONE
            AlertUtil.showToastShort(requireContext(), it)
        }

        // Navigate to home screen
        viewModel.navigateToHome.observe(viewLifecycleOwner){
            val consumer = it.customer
            consumer!!.id?.toInt()?.let { it1 ->
                val action = LoginFragmentDirections.actionLoginFragmentToMainActivity(
                    it1, consumer.mobileNoLogin!!
                )
                binding.root.findNavController().navigate(action)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {

        const val  LOGIN = "login"
        const val  GENERATE_OTP = "Generate Otp"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}