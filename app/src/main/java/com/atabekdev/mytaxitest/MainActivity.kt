package com.atabekdev.mytaxitest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.atabekdev.mytaxitest.common.service.LocationService
import com.atabekdev.mytaxitest.ui.screens.MapScreen
import com.atabekdev.mytaxitest.ui.theme.MyTaxiTestTheme
import com.atabekdev.mytaxitest.ui.viewmodel.LocationViewModel
import com.mapbox.geojson.Point
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTaxiTestTheme {
                val context = LocalContext.current
                var isGpsEnabled by remember { mutableStateOf(false) }
                var showDialog by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    isGpsEnabled = isLocationEnabled(context)
                    if (!isGpsEnabled) {
                        showDialog = true
                    }
                }

                DisposableEffect(lifecycle) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            // Check the location status again when the activity is resumed
                            isGpsEnabled = isLocationEnabled(context)
                            showDialog = !isGpsEnabled
                        }
                    }

                    lifecycle.addObserver(observer)
                    onDispose {
                        lifecycle.removeObserver(observer)
                    }
                }

                if (showDialog) {
                    EnableLocationDialog(onDismiss = { showDialog = false }) {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        context.startActivity(intent)
                    }
                }

                val requestPermissionLauncher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted: Boolean ->
                            if (isGranted) {
                                startService()
                            }
                        })

                LaunchedEffect(Unit) {
                    if (!hasLocationPermission(context)) {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        startService()
                    }
                }

                val initialPoint = Point.fromLngLat(-98.0, 39.5)
                MapScreen(viewModel, initialPoint, resources)
            }
        }
    }

    private fun startService() {
        Intent(this@MainActivity, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            this@MainActivity.startService(this)
        }
    }
}

@Composable
fun EnableLocationDialog(onDismiss: () -> Unit, onSettingsClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onSettingsClick) {
                Text("Настройки")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        title = {
            Text("Включите геолокацию")
        },
        text = {
            Text("Для корректной работы приложения включите геолокацию в настройках.")
        }
    )
}

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
