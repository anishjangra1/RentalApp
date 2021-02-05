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
import com.ride.databinding.ActivityGetStartedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedActivity : AppCompatActivity(){

    var btnCountinueWithPhone: TextView? = null
    lateinit var binding: ActivityGetStartedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityGetStartedBinding.inflate(layoutInflater).also {
            this@GetStartedActivity.setContentView(it.root)
        }

        initUi()
    }

    private fun initUi() {
        binding.btnCountinueWithPhone.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnCountinueWithPhone -> {
                val intent = Intent(this, GenerateOtpActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
