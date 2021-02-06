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
import androidx.lifecycle.observe
import com.ride.utility.AlertUtil
import com.ride.viewmodels.GenerateOtpViewModel
import com.ride.views.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateOtpActivity : AppCompatActivity() {
    private lateinit var etEnterOtp: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnGenerateOtp: TextView
    private lateinit var tvResendOtp: TextView
    private lateinit var privacyLayout: LinearLayout
    private lateinit var progressBar: ProgressBar

    private val viewModel: GenerateOtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generate_otp_screen)
        initUI()
        registerObserver()
    }

    private fun initUI() {

        etPhoneNumber = findViewById(R.id.etPhoneNumber)

        etEnterOtp = findViewById(R.id.etEnterOtp)

        btnGenerateOtp = findViewById(R.id.btnGenerateOtp)
        btnGenerateOtp!!.setOnClickListener(clickListener)

        privacyLayout = findViewById(R.id.privacyLayout)

        progressBar  = findViewById(R.id.loading_spinner)

        privacyLayout!!.setOnClickListener(clickListener)

        findViewById<TextView>(R.id.tvResendOtp).setOnClickListener{
            generateOtp()
        }


        etPhoneNumber!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(s?.length.toString().equals("10")){
                    btnGenerateOtp?.setBackgroundResource(R.drawable.button_rounded_active)
                    btnGenerateOtp!!.setTextColor(Color.parseColor("#000000"));
                }else{
                    btnGenerateOtp?.setBackgroundResource(R.drawable.button_rounded_inactive)
                    btnGenerateOtp!!.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
                viewModel.validateOtp(etEnterOtp!!.text.toString())
            }
        }
    }

    private fun registerObserver(){
        viewModel.verifyOtp.observe(this){
            progressBar!!.visibility = View.GONE
            AlertUtil.showToastShort(this, "Please enter Otp")
            etEnterOtp!!.visibility = View.VISIBLE
            btnGenerateOtp!!.text = LOGIN
        }

        viewModel.notValidNumber.observe(this){
            progressBar.visibility = View.GONE
            AlertUtil.showToastShort(this, "Please enter valid mobile number")
        }

        viewModel.showSnackBar.observe(this){
            progressBar.visibility = View.GONE
            AlertUtil.showToastShort(this, it)
        }

        // Navigate to home screen
        viewModel.navigateToHome.observe(this){
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }
    }

    companion object {
        const val  LOGIN = "login"
        const val  GENERATE_OTP = "Generate Otp"
    }
}