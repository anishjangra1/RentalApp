package com.ride.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RideViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Ride Fragment show available rides here"
    }
    val text: LiveData<String> = _text
}