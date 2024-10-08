package com.atabekdev.mytaxitest.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.atabekdev.mytaxitest.ui.screens.MapScreen
import com.atabekdev.mytaxitest.ui.theme.MyTaxiTestTheme
import com.atabekdev.mytaxitest.ui.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            MyTaxiTestTheme {
                MapScreen(LocalContext.current, viewModel)
            }
        }
    }
}
