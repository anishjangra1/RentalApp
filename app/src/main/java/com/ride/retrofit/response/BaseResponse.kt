package com.ride.retrofit.response

import java.io.Serializable

open class BaseResponse : Serializable {
    var status: String? = null
    var message: String? = null
    var statusCode: Int? = null
}