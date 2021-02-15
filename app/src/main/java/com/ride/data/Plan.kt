package com.ride.data

import com.google.gson.annotations.SerializedName

/**
{
"msg": "Ride Plans",
"status": 1,
}
}
 */
data class Plan (

        @SerializedName("id")
        var id: Int? = null,

        @SerializedName("plan")
        var plan: String? = null,

        @SerializedName("rate")
        var rate: Float? = null,

        @SerializedName("duration")
        var duration: Int? = null,

        @SerializedName("blocked")
        var blocked: Boolean? = null,

        @SerializedName("createdDate")
        var createdDate: String? = null,

        @SerializedName("updatedDate")
        var updatedDate: String? = null,

        @SerializedName("bpMasterId")
        var bpMasterId: Int? = null,

        var isPlanSelected: Boolean = false
){
}
