package com.ride

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class GetStartedActivity : AppCompatActivity(){


    var btnCountinueWithPhone: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_get_started)

        initUI()
    }

    private fun initUI() {
        btnCountinueWithPhone = findViewById(R.id.btnCountinueWithPhone)
        btnCountinueWithPhone!!.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnCountinueWithPhone -> {
                val intent = Intent(this, GenerateOtpActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    override fun onClick(view: View?) {
//        when (view!!.id) {
//            R.id.btnCountinueWithPhone -> {
//                val intent = Intent(this, GenerateOtpActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}
