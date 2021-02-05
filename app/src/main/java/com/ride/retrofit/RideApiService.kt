package com.ride.retrofit


import com.ride.retrofit.response.BaseResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RideApiService {


    @POST("customers/generate_login_otp")
    suspend fun generateOtpForRide(@Query("mobileNo") phoneNumber: String): Response<BaseResponse>

    @POST("customers/generate_login_otp")
    suspend fun generateOtpForLogin(
        @Query("mobileNo") phoneNumber: String
    ): Response<String>

    @POST("customers/generate_login_otp")
    suspend fun addCustomer(@Query("mobileNo") phoneNumber: String): Response<BaseResponse>

    @GET("vehicleType/get_nearby_vehicles")
    suspend fun sendOTP(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude:String
    ): Response<BaseResponse>


    companion object {

        private const val BASE_URL = "http://13.235.248.128:8080/"

        fun create(): RideApiService {

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
                .create(RideApiService::class.java)

        }
    }

}