package com.ride.data

import com.google.gson.annotations.SerializedName

/**
{
"msg": "Get Ride plan",
"status": 1,
}
}
 */
data class GetRidePlanResponse (
    @SerializedName("msg")
        var msg: String? = null,

    @SerializedName("status")
        var status: Int? = null,

    @SerializedName("customer")
        var plan: Plan? = null,
)