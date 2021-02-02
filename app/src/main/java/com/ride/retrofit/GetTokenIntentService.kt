package com.ride.retrofit

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

class GetTokenIntentService : IntentService("MyIntentService") {
    val log = Logger.getLogger(GetTokenIntentService::class.java.name)
    val mainHandler = Handler(Looper.getMainLooper())
    private var disposable: Disposable? = null

    private val apiService by lazy {
        ApiService.create()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mainHandler.post(object : Runnable {
            override fun run() {
                //Toast.makeText(applicationContext, "Service", Toast.LENGTH_SHORT).show()
                Log.e("SERVICE", "STARTED")
//                getToken()
                mainHandler.postDelayed(this, 1200000) /*20 minute*/
            }
        })

        //return super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY

    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


//    private fun getToken() {
//        if (!Utils.isConnected(this)) {
//            Utils.showAlert(this)
//            return
//        }
//        var profileData: ProfileData? = PreferenceHelper.getProfileData(this) ?: return
//        disposable = apiService.getToken(profileData?.data?._id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result ->
//                    if (result.statusCode == 200 && result.status.equals("Success")) {
//                        if (!TextUtils.isEmpty(result.token)) {
//                            PreferenceHelper.saveToken(applicationContext, result.token)
//                            PreferenceHelper.saveLabCartCount(applicationContext, "" + result.data?.labCartItems)
//                            PreferenceHelper.savePharmacyCartCount(applicationContext, "" + result.data?.pharmacyCartItems)
//                        }
//                    } else {
//                        val intent = Intent(applicationContext, LoginActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    }
//                },
//                { error ->
//                    val intent = Intent(applicationContext, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                }
//            )
//    }
}