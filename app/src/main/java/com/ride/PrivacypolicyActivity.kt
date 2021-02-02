package com.ride

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class PrivacypolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = WebView(this)
        view.settings.javaScriptEnabled = true
        view.loadUrl("file:///android_asset/privacy_policy.html")
        view.setBackgroundColor(Color.TRANSPARENT)
        setContentView(view)
    }
}