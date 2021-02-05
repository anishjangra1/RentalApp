package com.ride.data

import com.google.gson.annotations.SerializedName

/**
{
"msg": "New Customer",
"status": 1,
}
}
 */
data class ValidateOtpResponse (
        @SerializedName("msg")
        var msg: String? = null,

        @SerializedName("status")
        var status: Int? = null,

        @SerializedName("customer")
        var customer: Customer? = null,
)