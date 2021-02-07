package com.ride.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ride.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            this@MainActivity.setContentView(this.root)
        }

    }
}