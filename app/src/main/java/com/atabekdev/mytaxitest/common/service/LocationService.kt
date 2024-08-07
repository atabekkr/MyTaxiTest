package com.atabekdev.mytaxitest.common.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ServiceCompat
import androidx.core.content.PermissionChecker
import com.atabekdev.mytaxitest.common.extensions.toTimeDateString
import com.atabekdev.mytaxitest.common.locationnotification.LocationNotification
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.usecase.LocationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var addLocationUseCase: LocationUseCase

    @Inject
    lateinit var notification: LocationNotification

    override fun onBind(intent: Intent?) = null

    @SuppressLint("NewApi", "MissingPermission")
    override fun onCreate() {
        super.onCreate()

        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, INTERVAL, SMALLEST_DISTANCE, locListener
        )
        locManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, INTERVAL, SMALLEST_DISTANCE, locListener
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground()
        return super.onStartCommand(intent, flags, startId)

    }

    private fun startForeground() {
        val locationPermission =
            PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermission != PermissionChecker.PERMISSION_GRANTED) {
            stopSelf()
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.createChannel()
        }

        ServiceCompat.startForeground(
            /* service = */ this,
            /* id = */ ID, // Cannot be 0
            /* notification = */ notification.createNotification(),
            /* foregroundServiceType = */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            } else {
                0
            }
        )
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

    companion object {

        const val INTERVAL: Long = 3 * 1000
        const val SMALLEST_DISTANCE: Float = 1f
        const val CHANNEL_ID = "location_channel"
        const val ID = 100

    }
}