package com.ride
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ride.utility.AlertUtil
import com.ride.viewmodels.GenerateOtpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateOtpActivity : AppCompatActivity() {
    private lateinit var etEnterOtp: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnGenerateOtp: TextView
    private lateinit var tvResendOtp: TextView
    private lateinit var privacyLayout: LinearLayout
    private lateinit var mainContainer: CoordinatorLayout
    private lateinit var progressBar: ProgressBar

    private val viewModel: GenerateOtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.generate_otp_screen)
        initUI()
        registerObserver()
    }

    private fun initUI() {
        mainContainer = findViewById(R.id.main_container)



    }


    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnGenerateOtp -> {
                generateOtp()

            }
        }
    }

    private fun generateOtp(){
        when(btnGenerateOtp!!.text){
            GENERATE_OTP -> {
                progressBar.visibility = View.VISIBLE
                viewModel.generateOtp(etPhoneNumber!!.text.toString())
            }
            LOGIN -> {
                progressBar.visibility = View.VISIBLE
                viewModel.validateOtp(btnGenerateOtp!!.text.toString())
            }

        }
    }
    private fun navigateToHomePage(){

    }

    private fun registerObserver(){
        viewModel.verifyOtp.observe(this){
            progressBar!!.visibility = View.GONE
            AlertUtil.showToastShort(this, "Please enter Otp")
            etEnterOtp!!.visibility = View.VISIBLE
            btnGenerateOtp!!.text = "Login"
        }

        viewModel.notValidNumber.observe(this){
            progressBar.visibility = View.GONE
            AlertUtil.showToastShort(this, "Please enter valid mobile number")
        }
    }

    companion object {
        const val  LOGIN = "login"
        const val  GENERATE_OTP = "Generate Otp"
    }
}