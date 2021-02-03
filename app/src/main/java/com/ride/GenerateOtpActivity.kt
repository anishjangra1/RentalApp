package com.ride

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.giantcell.utils.Utils


class GenerateOtpActivity : AppCompatActivity() {
    private var etPhoneNumber: EditText? = null
    private var btnGenerateOtp: TextView? = null
    private var privacyLayout: LinearLayout? = null
    private var main_container: CoordinatorLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.generate_otp_screen)

        initUI()
    }

    private fun initUI() {
        main_container = findViewById(R.id.main_container)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        btnGenerateOtp = findViewById(R.id.btnGenerateOtp)
        privacyLayout = findViewById(R.id.privacyLayout)
        btnGenerateOtp!!.setOnClickListener(clickListener)
        privacyLayout!!.setOnClickListener(clickListener)


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
                if(etPhoneNumber!!.text.length ==10) {
                    val intent = Intent(this, BpMapActivity::class.java)
                    startActivity(intent)
                }
            }

            R.id.privacyLayout -> {
                    val intent = Intent(this, PrivacypolicyActivity::class.java)
                    startActivity(intent)

            }
        }
    }
}