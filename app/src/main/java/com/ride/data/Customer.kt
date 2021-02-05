package com.ride.data

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("ipId")
    var ipId: String? = null,

    @SerializedName("customerNo")
    var customerNo: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("mobileNoLogin")
    var mobileNoLogin: String? = null,

    @SerializedName("loginApproved")
    var loginApproved: Boolean? = false,

    @SerializedName("loginState")
    var loginState: Boolean? = false,

    @SerializedName("billToName")
    var billToName: String? = null,

    @SerializedName("billToPhone1")
    var billToPhone1: String? = null,

    @SerializedName("billToState")
    var billToState: String? = null,

    @SerializedName("billToAddress1")
    var billToAddress1: String? = null,

    @SerializedName("billToPhone2")
    var billToPhone2: String? = null,

    @SerializedName("billToCity")
    var billToCity: String? = null,

    @SerializedName("billToAdress2")
    var billToAdress2: String? = null,

    @SerializedName("billToPinCode")
    var billToPinCode: String? = null,

    @SerializedName("billToCountry")
    var billToCountry: String? = null,

    @SerializedName("billToGst")
    var billToGst: String? = null,


    @SerializedName("createdBy")
    var createdBy: String? = null,

    @SerializedName("createdDate")
    var createdDate: String? = null,

    @SerializedName("updatedDate")
    var updatedDate: String? = null,

    @SerializedName("otp")
    var otp: Int? = null,

    @SerializedName("bpmasterIdu")
    var bpmasterIdu: Int? = null,

    @SerializedName("bpMasterId")
    var bpMasterId: Int? = null,
)