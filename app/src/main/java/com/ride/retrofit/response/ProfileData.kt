package com.ride.retrofit.response

class ProfileData {
    var status: String? = null
    var message: String? = null
    var statusCode: Int? = null
    var token: String? = null
    var data: Data? = null

    class Data {
        var _id: String? = null
        var email: String? = null
        var firstName: String? = null
        var lastName: String? = null
        var gender: String? = null
        var dob: String? = null
        var mobileNumber: String? = null
        var registerationType: String? = null
        var firebaseToken: String? = null
        var isValidateEmail: Boolean? = null
        var image: String? = null
        var labCartItems: Int? = null
        var pharmacyCartItems: Int? = null
        var address: Address? = null
    }

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