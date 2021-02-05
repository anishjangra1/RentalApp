package com.ride.data

import com.ride.retrofit.RideApiService
import retrofit2.Response
import java.net.ResponseCache
import javax.inject.Inject

class AppRepository @Inject constructor(private val serviceRide: RideApiService){

    suspend fun generateOtpForRide(mobileNumber: String){
        serviceRide.generateOtpForRide(mobileNumber)
    }

    suspend fun generateOtForLogin(mobileNumber: String): Response<String>{
        return serviceRide.generateOtpForLogin(mobileNumber)
    }

}