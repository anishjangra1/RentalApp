package com.ride.utility

class Util {

    companion object {
        fun isValidMobileNumber(number: String) = !number.isNullOrBlank() && number.length  >= 10
    }
}