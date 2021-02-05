package com.ride.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ride.data.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenerateOtpViewModel @Inject constructor(val repository: AppRepository) : ViewModel() {

    private var _updateOtp = MutableLiveData<String>()
    val verifyOtp = _updateOtp

    fun generateOtp(mobileNumber: String){
        viewModelScope.launch(Dispatchers.IO) {
            val otp = repository.generateOtForLogin(mobileNumber).body()
            verifyOtp.value = otp
        }
    }
}