package com.ride.utils


import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(var space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.set(space, space, space, space)
//        outRect.top = space
//        outRect.left = space
//        outRect.right = space
//        outRect.bottom = space

//        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildLayoutPosition(view) == 0) {
//            outRect.top = 0
//        } else {
//            outRect.top = 0
//        }
    }
}