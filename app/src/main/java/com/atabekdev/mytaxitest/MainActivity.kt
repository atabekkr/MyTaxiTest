package com.atabekdev.mytaxitest

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.atabekdev.mytaxitest.ui.screens.MapScreen
import com.atabekdev.mytaxitest.ui.theme.MyTaxiTestTheme
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTaxiTestTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                var userLocation by remember { mutableStateOf<Point?>(null) }

                // Create a permission launcher
                val requestPermissionLauncher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted: Boolean ->
                            if (isGranted) {
                                // Permission granted, update the location
                                getCurrentLocation(context) { lat, long ->
                                    userLocation = Point.fromLngLat(long, lat)
                                }
                            }
                        })

                LaunchedEffect(Unit) {
                    if (!hasLocationPermission(context)) {
                        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        getCurrentLocation(context) { lat, long ->
                            userLocation = Point.fromLngLat(long, lat)
                        }
                    }
                }

                val initialPoint = Point.fromLngLat(-98.0, 39.5)

                MapScreen(userLocation, initialPoint, resources)
            }
        }
    }
}
private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
}
