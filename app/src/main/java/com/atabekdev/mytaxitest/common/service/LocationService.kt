package com.atabekdev.mytaxitest.common.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.atabekdev.mytaxitest.common.extensions.toTimeDateString
import com.atabekdev.mytaxitest.common.locationnotification.LocationNotification
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.usecase.AddLocationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    companion object {
        const val ACTION_START = "actionStart"
        const val ACTION_STOP = "actionStop"

        const val INTERVAL: Long = 3 * 1000
        const val SMALLEST_DISTANCE: Float = 1f
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var addLocationUseCase: AddLocationUseCase

    @Inject
    lateinit var notification: LocationNotification

    override fun onBind(intent: Intent?) = null

    @SuppressLint("NewApi", "MissingPermission")
    override fun onCreate() {
        super.onCreate()

        notification.createChannel()

        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, INTERVAL, SMALLEST_DISTANCE, locListener
        )
        locManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, INTERVAL, SMALLEST_DISTANCE, locListener
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startForeground()
            ACTION_STOP -> stop()
        }
        return START_STICKY
    }

    private fun startForeground() {
        Log.d("TTTT", "startw")
        startForeground(LocationNotification.ID, notification.createNotification())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private var locManager: LocationManager? = null
    private val locListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(loc: Location) {
            notification.updateContentText(
                latitude = loc.latitude, longitude = loc.longitude
            )
            scope.launch {
                val currentTime = System.currentTimeMillis().toTimeDateString()
                addLocationUseCase.addLocation(
                    UserLocation(
                        lat = loc.latitude,
                        lng = loc.longitude,
                        storedAt = currentTime
                    )
                )
            }
        }

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }
}