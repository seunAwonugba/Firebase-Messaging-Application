package com.example.myfirebasecloudmessagingapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.myfirebasecloudmessagingapp.R
import com.example.myfirebasecloudmessagingapp.constant.Constants.CHANNEL_ID
import com.example.myfirebasecloudmessagingapp.constant.Constants.body
import com.example.myfirebasecloudmessagingapp.constant.Constants.title
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //handle messages
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        var bundle = Bundle()
        if(remoteMessage.data.isNotEmpty()){
            title = remoteMessage.data.getValue("TITLE")
            body = remoteMessage.data.getValue("BODY")

            bundle.putString("TITLE", title)
            bundle.putString("BODY", body)
            bundle.putString("TYPE", 0.toString()) //0 is for data

            val sharedPref = applicationContext.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("TITLE", title)
                putString("BODY", body)
                apply()
            }

            sentNotifications(title, body, bundle)

        }

                //if incoming message is a notification, get the contents
        if(remoteMessage.notification != null){
            title = remoteMessage.notification!!.title!!
            body = remoteMessage.notification!!.body!!

            Log.d("NOT_TAG", title)

            bundle.putString("TITLE", title)
            bundle.putString("BODY", body)
            bundle.putString("TYPE", 1.toString()) //1 is for data

            val sharedPref = this.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("TITLE", title)
                putString("BODY", body)
                apply()
            }



            sentNotifications(title, body, bundle)

        }

    }

    //handle sent notifications
    private fun sentNotifications(title: String?, body: String?, bundle: Bundle) {
        //intent triggers when there is a sent message
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        //(optional)chose a ring tone
        val ringToneUris = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        //Create a channel and set the importance
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setSound(ringToneUris, audioAttributes)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.notificationsFragment)
            .setArguments(bundle)
            .createPendingIntent()

        //set sent notification to the notification content
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.autochek_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(ringToneUris)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0,builder.build())

        Log.d("BODY_TAG", body.toString())

        val sharedPref = applicationContext.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("TITLE", title)
            putString("BODY", body)
            apply()
        }

    }
}