package com.ride.retrofit

import com.ride.data.Plan
import com.ride.data.ValidateOtpResponse
import com.ride.data.Vehicle
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RestApiService {

    @POST("customers/generate_login_otp")
    suspend fun generateOtpForRide(@Query("mobileNo") phoneNumber: String): Response<String>

    @POST("customers/generate_otp")
    suspend fun generateOtpForLogin(
        @Query("mobileNo") phoneNumber: String
    ): Response<String>

    @POST("customers/login")
    suspend fun validateOtp(
        @Query("mobileNo") phoneNumber: String,
        @Query("otp") otp: String,
        @Query("bpId") bpId: Int
    ): Response<ValidateOtpResponse>


    @GET("vehicleType/get_nearby_vehicles")
    suspend fun getNearbyVehicles(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float
    ): Response<List<Vehicle>>


    @GET("rental_plan/all")
    suspend fun getPlan(): Response<List<Plan>>


    companion object {

        private const val BASE_URL = "http://13.235.248.128:8080/"

        fun create(): RestApiService {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(Utility.okHttpClient)
                .build()
                .create(RestApiService::class.java)

        }
    }

}