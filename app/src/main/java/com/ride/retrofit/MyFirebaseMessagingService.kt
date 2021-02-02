package com.ride.retrofit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ride.R
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "NOTIFICATION_DATA"
    private lateinit var notificationManager: NotificationManager
    private val ADMIN_CHANNEL_ID = "Android4Dev"

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e(TAG, p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.let { message ->
            message.notification?.body?.let { Log.i(TAG, it) }

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //Setting up Notification channels for android O and above
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                setupNotificationChannels()
            }
            val notificationId = Random().nextInt(60000)

//            val intent = Intent(this, NotificationsActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

//            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher_round)  //a resource for your custom small icon
//                .setContentTitle(message.notification?.title) //the "title" value you sent in your notification
//                .setContentText(message.notification?.body) //ditto
//                .setAutoCancel(true)  //dismisses the notification on click
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//
//            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build())

        }

    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private fun setupNotificationChannels() {
//        val adminChannelName = getString(R.string.app_name)
//        val adminChannelDescription = getString(R.string.addToCart)
//
//        val adminChannel: NotificationChannel
//        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
//        adminChannel.description = adminChannelDescription
//        adminChannel.enableLights(true)
//        adminChannel.lightColor = Color.RED
//        adminChannel.enableVibration(true)
//        notificationManager.createNotificationChannel(adminChannel)
//    }
}