package com.ride.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ride.data.AppRepository
import com.ride.data.ValidateOtpResponse
import com.ride.data.Vehicle
import com.ride.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
    ): ViewModel(){

    private var _showSnackBar = MutableLiveData<String>()
    val showSnackBar = _showSnackBar

    private var _availableVehicles = MutableLiveData<List<Vehicle>>()
    val availableVehicles = _availableVehicles


    private var receivedOtp: String? = null
    private var userMobileNumber: String? = null


    fun getNearbyVehicles(){

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getNearbyVehicles(28.656358f, 77.24312f)
            if(response.isSuccessful){
                println("$TAG getNearbyVehicles() ->  ${response.body()}")
                availableVehicles.postValue(response.body())
            }
            else{
                println("$TAG getNearbyVehicles() ->  response failed ${response.body()}")
            }
        }
    }

    companion object {
        const val TAG = "@MapViewModel"
    }
}