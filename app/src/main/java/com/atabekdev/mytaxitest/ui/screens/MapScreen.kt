package com.atabekdev.mytaxitest.ui.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atabekdev.mytaxitest.R
import com.atabekdev.mytaxitest.ui.components.AddPointer
import com.atabekdev.mytaxitest.ui.components.LiftBottomSheetCard
import com.atabekdev.mytaxitest.ui.components.MainCard
import com.atabekdev.mytaxitest.ui.components.SheetContent
import com.atabekdev.mytaxitest.ui.components.SwitchStatus
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    userLocation: Point?,
    initialPoint: Point,
    resources: Resources,
) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = true)
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.Transparent,
        sheetShadowElevation = 0.dp,
        sheetContent = { SheetContent() },
        sheetPeekHeight = 200.dp, // Adjust this value to set initial peek height
        sheetDragHandle = { BottomSheetDefaults.DragHandle(modifier = Modifier.padding(top = 30.dp)) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
                Modifier.fillMaxSize(),
                mapViewportState = MapViewportState().apply {
                    setCameraOptions {
                        zoom(14.0)
                        center(userLocation ?: initialPoint)
                        pitch(0.0)
                        bearing(0.0)
                    }
                },
            ) {
                AddPointer(point = userLocation ?: initialPoint, resources = resources)
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
                            containerColor = Color.White,
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
                            containerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {}
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(resources.getColor(R.color.green))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "95",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.font_lato_bold))
                                )
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
                    LiftBottomSheetCard(onClick = {
                    })
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
