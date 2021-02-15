package com.ride.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters{

    @BindingAdapter("app:ridePlanPrice")
    @JvmStatic
    fun onSubscriptionPlanPrice(
        view: TextView,
        price: Float?,
    ) {
        "₹$price".also { view.text = it }
    }
}