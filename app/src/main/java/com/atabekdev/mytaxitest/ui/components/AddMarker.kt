package com.atabekdev.mytaxitest.ui.components

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.atabekdev.mytaxitest.R
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation

@OptIn(MapboxExperimental::class)
@Composable
fun AddMarker(point: Point, resources: Resources) {
    val drawable = ResourcesCompat.getDrawable(
        resources,
        R.drawable.marker_car,
        null
    )
    val bitmap = drawable?.toBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    PointAnnotation(
        iconImageBitmap = bitmap,
        iconSize = 0.2,
        point = point
    )
}