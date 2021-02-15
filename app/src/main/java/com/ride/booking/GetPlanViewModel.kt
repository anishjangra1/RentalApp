package com.ride.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ride.data.AppRepository
import com.ride.data.GetPlansResponse
import com.ride.data.Plan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPlanViewModel @Inject internal constructor(
    private val repository: AppRepository
    ) : ViewModel() {

    private lateinit var getPlansResponse: GetPlansResponse

    private var _planResponseList = MutableLiveData<List<Plan>>()
    var planResponseList = _planResponseList

    private var _changePlan = MutableLiveData<Plan>()
    var changePlan = _changePlan

    fun getPlans(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRidePlans()
            if(response.isSuccessful){
                getPlansResponse = GetPlansResponse(response.body()!!)
                planResponseList.postValue(getPlansResponse.plans)
                println("$TAG getPlans() ->  ${getPlansResponse.plans}")
//                verifyOtp.postValue(response.body())
            }
            else{
                println("$TAG getPlans() ->  response failed ${response.body()}")
            }
        }
    }

    fun selectPlan(plan: Plan){
        getPlansResponse.selectPlan(plan)
        changePlan.value = plan
    }


    companion object {
        const val TAG = "@GetPlanViewModel"
    }
}