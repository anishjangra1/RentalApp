package com.ride.data

import com.ride.retrofit.RestApiService
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(private val service: RestApiService){

    suspend fun generateOtpForRide(mobileNumber: String){
        service.generateOtpForRide(mobileNumber)
    }

    suspend fun generateOtForLogin(mobileNumber: String): Response<String>{
        return service.generateOtpForLogin(mobileNumber)
    }

    suspend fun validateOtp(
        mobileNumber: String,
        otp: String,
        bpId: Int
    ): Response<ValidateOtpResponse>{
        return service.validateOtp(mobileNumber, otp, bpId)
    }

    suspend fun getNearbyVehicles(latitude: Float, longitude: Float): Response<List<Vehicle>>{
        return service.getNearbyVehicles(latitude, longitude)
    }

    suspend fun getRidePlans(): Response<List<Plan>>{
        return service.getPlan()
    }

}