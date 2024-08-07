package com.atabekdev.mytaxitest.ui.screens

import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atabekdev.mytaxitest.R
import com.atabekdev.mytaxitest.ui.components.AddMarker
import com.atabekdev.mytaxitest.ui.components.LiftBottomSheetCard
import com.atabekdev.mytaxitest.ui.components.MainCard
import com.atabekdev.mytaxitest.ui.components.SheetContent
import com.atabekdev.mytaxitest.ui.components.SwitchStatus
import com.atabekdev.mytaxitest.ui.theme.Green
import com.atabekdev.mytaxitest.ui.viewmodel.LocationViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    locationViewModel: LocationViewModel,
    initialPoint: Point,
    resources: Resources,
) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )

    val latestLocation = locationViewModel.lastLocation.collectAsState()
    val latestPoint =
        latestLocation.value?.let { Point.fromLngLat(it.lng, it.lat) }

    val coroutineScope = rememberCoroutineScope()

    val isRowVisible by remember {
        derivedStateOf {
            scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded
        }
    }
    val mapViewportState = rememberMapViewportState {
        // Set the initial camera position
        setCameraOptions {
            center(latestPoint ?: initialPoint)
            zoom(14.0)
            pitch(0.0)
            bearing(0.0)
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
                mapViewportState = mapViewportState,
                onMapClickListener = {
                    Log.d("TTTT", "Click!")
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
            ) {
                AddMarker(latestPoint ?: initialPoint, resources = resources)
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
                    Card(
                        modifier = Modifier.size(56.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {}
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "img1"
                            )
                        }
                    }
                    SwitchStatus(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    Card(
                        modifier = Modifier.size(56.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {}
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Green),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "95",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
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
                            MainCard(R.drawable.ic_plus)
                            Spacer(modifier = Modifier.height(16.dp))
                            MainCard(R.drawable.ic_minus)
                            Spacer(modifier = Modifier.height(16.dp))
                            MainCard(R.drawable.ic_navigation)
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
