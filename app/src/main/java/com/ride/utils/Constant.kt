package com.ride.utils


object Constant {
    // Fields from build type: debug
    val BASE_URL = "http://34.93.184.243/"
    val RAZORPAY_ID = "rzp_test_hAidb7lYlbRe5r"
    var POSITION = "position"
    var numberOfColumns = 2
    var PHONE_NUMBER = "phone_number"
    var EMAIL = "email"
    var LAB_DATA = "lab_data"

    var FROM = "from"

    var CATEGORY_ID = "categoryId"
    var CATEGORY_TYPE = "categoryType"

    var FROM_LAB = "from_lab"

    const val LOCATION_REQUEST = 1000
    const val GPS_REQUEST = 1001

    enum class NotificationType {
        lab,
        pharmacy,
        general
    }

    enum class PrescriptionType {
        lab,
        pharmacy,
        independent
    }
}

