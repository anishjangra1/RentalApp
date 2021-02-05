package com.ride.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ride.data.AppRepository
import com.ride.data.PlanResponse
import com.ride.data.ValidateOtpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPlanViewModel @Inject internal constructor(
    private val repository: AppRepository
    ) : ViewModel() {
    private var _planResponseList = MutableLiveData<List<PlanResponse>>()
    var planResponseList = _planResponseList
    fun getPlans(){

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRidePlans()
            if(response.isSuccessful){
                planResponseList.postValue(response.body())
                println("$TAG generateOtp() ->  ${response.body()}")
//                verifyOtp.postValue(response.body())
            }
            else{
                println("$TAG generateOtp() ->  response failed ${response.body()}")
            }
        }
    }


    companion object {
        const val TAG = "@GetPlanViewModel"
    }
}