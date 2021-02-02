package com.ride.retrofit


import com.ride.utils.Constant.BASE_URL
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(Utility.okHttpClient)
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }


//    @POST("auth/phlebotomist/login")
//    fun login(@Body body: JsonObject): Observable<LoginResponseData>
//
//
//    @GET("auth/phlebotomist/passwordReset")
//    fun getOtp(@Query("email") email: String): Observable<OtpResponseData>
//
//
//    @GET("auth/getUserToken")
//    fun getToken(@Query("userId") userId: String?): Observable<ProfileData>
//
//
//    @GET("auth/phlebotomist/logout")
//    fun logout(
//        @Header("Authorization") Authorization: String,
//        @Query("firebaseToken") firebaseToken: String
//    ): Observable<ProfileData>
//
//    @GET("api/lab/restPhlebotomist/profile")
//    fun getProfile(@Header("Authorization") Authorization: String): Observable<ProfileData>
//
//    @GET("api/lab/restPhlebotomist/appointments")
//    fun getAssignedList(@Header("Authorization") Authorization: String): Observable<AssignedTestsResponseData>
//
//    @PUT("auth/phlebotomist/passwordReset")
//    fun validatePhoneOtp(
//        @Body body: JsonObject?
//    ): Observable<ProfileData>
//
//    //for lab test apis
//    @GET("api/lab/restPhlebotomist/getSlots")
//    fun getTimeSlotsList(
//        @Header("Authorization") Authorization: String,
//        @Query("userId") userId: String?
//    ): Observable<TimeSlotData>


//    @POST("user/signup/validateOneTimePassword")
//    fun validateOtp(@Body body: JsonObject): Observable<LoginResponseData>
//
//    @Headers("Content-Type: application/json")
//    @PUT("user/password/change")
//    fun passwordChange(
//            @Header("Authorization") Authorization: String,
//            @Body body: JsonObject?
//    ): Observable<ResultEntity>
//
//
//    @POST("user/forgotPassword/validateEmail")
//    fun forgotPassword(@Body body: JsonObject?): Observable<ResultEntity>
//
//    @POST("user/forgotPassword/validateOneTimePassword")
//    fun forgotPasswordValidate(@Body body: JsonObject?): Observable<ResultEntity>
//
//    @GET("api/appointment/restHospital/searchHospital")



}