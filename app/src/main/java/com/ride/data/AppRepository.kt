package com.ride.data

import com.ride.retrofit.RestApiService
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(private val serviceRest: RestApiService){

    suspend fun generateOtpForRide(mobileNumber: String){
        serviceRest.generateOtpForRide(mobileNumber)
    }

    suspend fun generateOtForLogin(mobileNumber: String): Response<String>{
        return serviceRest.generateOtpForLogin(mobileNumber)
    }
    suspend fun getRidePlans(): Response<String>{
        return serviceRest.getPlan()
    }

}