package com.ride.retrofit.request

class RequestUpdateProfileData {
    var firstName: String? = null
    var lastName: String? = null
    var image: String? = null
    var dob: String? = null
    var gender: String? = null
    var mobileNumber: String? = null
    var email: String? = null
    var address: Address? = null

    class Address {
        var address1: String? = null
        var address2: String? = null
        var city: String? = null
        var state: String? = null
        var pinCode: String? = null
        var country: String? = null
        var latitue: String? = null
        var longitude: String? = null
    }

}