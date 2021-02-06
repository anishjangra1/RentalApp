package com.ride.data

import com.google.gson.annotations.SerializedName

/**
"id": 2,
"name": "Name22",
"vehicleNo": "BP1V002",
"type": null,
"latitude": 28.656258,
"longitude": 77.24112,
"inventory": null,
"bpMasterId": 1,
"available": true,
"createdDate": "2021-02-04T15:06:06.000+00:00",
"updatedDate": "2021-02-04T15:06:06.000+00:00"
 */
data class Vehicle (
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("vehicleNo")
    var vehicleNo: String? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("latitude")
    var latitude: Float? = null,

    @SerializedName("longitude")
    var longitude: Float? = null,

    @SerializedName("inventory")
    var inventory: Boolean? = false,

    @SerializedName("bpMasterId")
    var bpMasterId: Int? = null,

    @SerializedName("available")
    var available: Boolean? = false,

    @SerializedName("createdDate")
    var createdDate: String? = null,

    @SerializedName("updatedDate")
    var updatedDate: String? = null,
        ){


}