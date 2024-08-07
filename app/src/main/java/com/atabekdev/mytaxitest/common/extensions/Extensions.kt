package com.atabekdev.mytaxitest.common.extensions

import android.Manifest
import android.app.ForegroundServiceStartNotAllowedException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.atabekdev.mytaxitest.common.service.LocationService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return format.format(dateTime)
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.startLocationService() {
    startForegroundService(Intent(this, LocationService::class.java))
}
