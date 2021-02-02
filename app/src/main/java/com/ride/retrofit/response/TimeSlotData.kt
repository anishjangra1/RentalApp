package com.ride.retrofit.response

import java.io.Serializable

class TimeSlotData : BaseResponse() {
    var data: ArrayList<Data>? = null

    class Data : Serializable {
        var date: String? = null
        var slots: ArrayList<Slots>? = null
    }

    class Slots : Serializable {
        var slot: String? = null
        var isBooked: Boolean? = null
    }
}