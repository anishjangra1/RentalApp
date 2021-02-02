package com.ride.retrofit.response

import java.io.Serializable

class LoginResponseData {
    var status: String? = null
    var message: String? = null
    var code: Int? = null
    var statusCode: Int? = null
    var token: String? = null
    var data: Data? = null

    class Data : Serializable{
        var _id: String? = null
        var firstName: String? = null
        var lastName: String? = null
        var email: String? = null
        var mobileNumber: String? = null
        var gender: String? = null
        var registrationType: String? = null
        var isValidateEmail: Boolean? = null
    }
}


