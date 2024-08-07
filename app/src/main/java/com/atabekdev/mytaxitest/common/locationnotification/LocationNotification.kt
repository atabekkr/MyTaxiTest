package com.atabekdev.mytaxitest.common.locationnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.atabekdev.mytaxitest.ui.MainActivity
import com.atabekdev.mytaxitest.R
import com.atabekdev.mytaxitest.common.service.LocationService.Companion.CHANNEL_ID
import com.atabekdev.mytaxitest.common.service.LocationService.Companion.ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationNotification @Inject constructor(
    @ApplicationContext private val context: Context,
    private val manager: NotificationManager,
) {

    private val builder: NotificationCompat.Builder by lazy {

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.tracking_location))
            .setContentText(context.getString(R.string.your_location, "-", "-"))
            .setSmallIcon(R.drawable.marker_car)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel() {
        NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.location_tracker),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.location_tracker_description)
            manager.createNotificationChannel(this)
        }
    }

    fun createNotification(): Notification {
        return builder.build()
    }

    fun updateContentText(latitude: Double, longitude: Double) {
        val notification =
            builder.setContentText(
                context.getString(
                    R.string.your_location,
                    latitude.toString(),
                    longitude.toString()
                )
            ).build()
        manager.notify(ID, notification)
    }
}