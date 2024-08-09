package com.atabekdev.mytaxitest.ui.screens

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.atabekdev.mytaxitest.R
import com.atabekdev.mytaxitest.common.extensions.hasLocationPermission
import com.atabekdev.mytaxitest.common.extensions.startLocationService
import com.atabekdev.mytaxitest.ui.components.AddMarker
import com.atabekdev.mytaxitest.ui.components.Card95
import com.atabekdev.mytaxitest.ui.components.Controller
import com.atabekdev.mytaxitest.ui.components.HamburgerCard
import com.atabekdev.mytaxitest.ui.components.LiftBottomSheetCard
import com.atabekdev.mytaxitest.ui.components.SheetContent
import com.atabekdev.mytaxitest.ui.components.SwitchStatus
import com.atabekdev.mytaxitest.ui.intent.LocationIntent
import com.atabekdev.mytaxitest.ui.viewmodel.LocationViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.ComposeMapInitOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, MapboxExperimental::class)
@Composable
fun MapScreen(
    context: Context,
    locationViewModel: LocationViewModel,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )

    LaunchedEffect(locationViewModel) {
        locationViewModel.handleIntent(LocationIntent.GetLocations)
    }

    val latestLocation = locationViewModel.lastLocation.collectAsState()
    val latestPoint =
        latestLocation.value?.let { Point.fromLngLat(it.lng, it.lat) }
    val initialPoint = Point.fromLngLat(-98.0, 39.5)

    val coroutineScope = rememberCoroutineScope()

    val isRowVisible by remember {
        derivedStateOf {
            scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded
        }
    }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(latestPoint ?: initialPoint)
            zoom(14.0)
            pitch(0.0)
            bearing(0.0)
        }
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    context.startLocationService()
                }
            })

    LaunchedEffect(Unit) {
        if (!context.hasLocationPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            context.startLocationService()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.Transparent,
        sheetShadowElevation = 0.dp,
        sheetContent = { SheetContent() },
        sheetPeekHeight = 200.dp, // Adjust this value to set initial peek height
        sheetDragHandle = { BottomSheetDefaults.DragHandle(modifier = Modifier.padding(top = 30.dp)) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MapboxMap(
                Modifier
                    .fillMaxSize(),
                composeMapInitOptions = ComposeMapInitOptions(
                    LocalDensity.current.density,
                    textureView = true
                ),
                mapViewportState = mapViewportState,
                onMapClickListener = {
                    coroutineScope.launch {
                        if (scaffoldState.bottomSheetState.hasExpandedState) {
                            scaffoldState.bottomSheetState.partialExpand()
                        } else {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                    true
                },
                style = {
                    if (isSystemInDarkTheme()) MapStyle(style = Style.DARK) else MapStyle(style = Style.TRAFFIC_DAY)
                },
                compass = {},
                logo = {},
                scaleBar = {},
                attribution = {},
            ) {
                AddMarker(latestPoint ?: initialPoint, resources = context.resources)
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HamburgerCard()
                    SwitchStatus(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    Card95()
                }
                Spacer(modifier = Modifier.height(200.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedVisibility(
                        visible = isRowVisible,
                        enter = slideInHorizontally { it / 2 } + fadeIn(),
                        exit = slideOutHorizontally { it / 2 } + fadeOut()
                    ) {
                        LiftBottomSheetCard(
                            onClick = {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            },
                        )
                    }
                    AnimatedVisibility(
                        visible = isRowVisible,
                        enter = slideInHorizontally { -it / 2 } + fadeIn(),
                        exit = slideOutHorizontally { -it / 2 } + fadeOut()
                    ) {
                        Column {
                            val currentZoom = mapViewportState.cameraState?.zoom ?: 14.0
                            Controller(R.drawable.ic_plus) {
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(latestPoint)
                                        zoom(currentZoom + 1.0)
                                        pitch(0.0)
                                        bearing(0.0)
                                    },
                                    MapAnimationOptions.mapAnimationOptions { duration(500) }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Controller(R.drawable.ic_minus) {
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(latestPoint)
                                        zoom(currentZoom - 1.0)
                                        pitch(0.0)
                                        bearing(0.0)
                                    },
                                    MapAnimationOptions.mapAnimationOptions { duration(500) }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Controller(R.drawable.ic_navigation) {
                                mapViewportState.flyTo(
                                    cameraOptions = cameraOptions {
                                        center(latestPoint ?: initialPoint)
                                        zoom(currentZoom)
                                        pitch(0.0)
                                        bearing(0.0)
                                    },
                                    MapAnimationOptions.mapAnimationOptions { duration(1000) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    mapViewportState.flyTo(
        cameraOptions = cameraOptions {
            center(latestPoint)
            zoom(14.0)
            pitch(0.0)
            bearing(0.0)
        },
        MapAnimationOptions.mapAnimationOptions { duration(2000) }
    )
}
