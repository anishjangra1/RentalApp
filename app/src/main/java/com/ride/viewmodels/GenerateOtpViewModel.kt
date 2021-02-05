package com.ride.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ride.data.AppRepository
import com.ride.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateOtpViewModel @Inject internal constructor(
    private val repository: AppRepository
    ) : ViewModel() {

    private var _updateOtp = MutableLiveData<String>()
    val verifyOtp = _updateOtp

    private var _notValidNumber = MutableLiveData<Boolean>()
    val notValidNumber = _notValidNumber

    private var _showSnackBar = MutableLiveData<String>()
    val showSnackBar = _showSnackBar

    var receivedOtp: String? = null
    var userMobileNumber: String? = null

    fun generateOtp(mobileNumber: String){
        if(!Util.isValidMobileNumber(mobileNumber)){
            _notValidNumber.value = true
            return
        }
        userMobileNumber = mobileNumber

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.generateOtForLogin(userMobileNumber!!)
            if(response.isSuccessful){
                println("$TAG generateOtp() ->  ${response.body()}")
                receivedOtp = response.body()
                verifyOtp.postValue(receivedOtp)
            }
            else{
                println("$TAG generateOtp() ->  response failed ${response.body()}")
            }
        }
    }

    fun validateOtp(otp: String){
        if(otp.isNullOrBlank()) {
            _showSnackBar.value = "Please enter otp"
            return
        }

        if(otp != receivedOtp){
            _showSnackBar.value = "Please enter valid otp"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.validateOtp(otp, userMobileNumber!!, 1)
            if(response.isSuccessful){
                println("$TAG generateOtp() ->  ${response.body()}")
            }
            else{
                println("$TAG generateOtp() ->  response failed ${response.body()}")
            }
        }
    }


    companion object {
        const val TAG = "@GenerateOtpViewModel"
    }
}