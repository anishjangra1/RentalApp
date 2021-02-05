package com.ride.utility

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Naresh on 10/4/2016.
 */

@SuppressLint("StaticFieldLeak")
object AlertUtil {

    fun showSnackBarShort(context: Context?, view: View, msg: String) {
        if (context != null) {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    @Suppress("NAME_SHADOWING")
    fun showSnackBarLong(
        context: Context?,
        view: View,
        msg: String,
        buttonTitle: String,
        onClickListener: View.OnClickListener
    ) {
        if (context != null) {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            snackbar.setAction(buttonTitle) { view -> onClickListener.onClick(view) }
            snackbar.show()
        }
    }

    @Suppress("UNUSED_VARIABLE")
    fun showSnackBarLong(context: Context?, view: View?, msg: String) {
        if (context != null && view != null) {
            var snackbar = view.let { Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show() }
        }
    }

    fun showToastShort(context: Context?, msg: String) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(context: Context?, msg: String) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

}
