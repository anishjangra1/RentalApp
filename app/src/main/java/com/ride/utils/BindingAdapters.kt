package com.ride.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ride.R

object BindingAdapters{

    @BindingAdapter("app:amount")
    @JvmStatic
    fun setPrice(
        view: TextView,
        amount: Float?,
    ) {
          view.text = view.context.getString(R.string.text_price_is, amount)
    }

    @BindingAdapter("app:duration")
    @JvmStatic
    fun setDuration(
        view: TextView,
        duration: Int?,
    ) {
        view.text = view.context.getString(R.string.text_duration_is, duration.toString())
    }
}