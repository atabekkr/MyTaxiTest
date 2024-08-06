package com.atabekdev.mytaxitest.common.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return format.format(dateTime)
}