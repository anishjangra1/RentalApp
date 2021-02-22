package com.ride.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private var _showBottomNavigation = MutableLiveData<Boolean>()
    val showBottomNavigation = _showBottomNavigation

    fun showBottomNavigation(show: Boolean)
    {
        showBottomNavigation.value = show
    }
}