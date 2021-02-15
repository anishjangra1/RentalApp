package com.ride.adapters

import com.ride.data.Plan

class  ItemListener<T>( val clickListener: (t: T) -> Unit){
    fun onClick(t: T) = run {
        clickListener(t)
    }
}