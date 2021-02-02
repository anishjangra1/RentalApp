package com.ride.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.text.TextUtils
import com.ride.R


class PrescriptionRequiredDialog {
    var runnable: Runnable? = null
    var description: String? = null

    fun openDialog(activity: Activity?, message: String, mRunnable: Runnable?): Boolean {
        description = if (TextUtils.isEmpty(message)) {
            activity?.getString(R.string.app_name);
        } else {
            message
        }
        runnable = mRunnable
        val dialog = AlertDialog.Builder(activity).create()
        dialog.setTitle(activity?.getString(R.string.app_name))
        dialog.setMessage(description)
        dialog.setCancelable(false)
        dialog.setIcon(R.drawable.ic_warning);
        dialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            activity?.getString(R.string.app_name)
        ) { dialog, buttonId -> runnable!!.run() }
        dialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            activity?.getString(R.string.app_name)
        ) { dialog, buttonId -> dialog.dismiss() }
        dialog.show()
        return true
    }
}