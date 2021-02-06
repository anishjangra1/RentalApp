package com.ride.utility

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Util {

    companion object {
        fun isValidMobileNumber(number: String) = !number.isNullOrBlank() && number.length  >= 10

        fun  <T: Any> fromJson(data: String): T{
            return Gson().fromJson<T>(data, Any::class.java) as T
        }

        fun <T> toJson(t: T): String{
            return Gson().toJson(t)
        }
    }
}