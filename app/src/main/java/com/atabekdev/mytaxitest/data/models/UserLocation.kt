package com.atabekdev.mytaxitest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userLocation")
data class UserLocation(
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lng") val lng: Double,
    @PrimaryKey @ColumnInfo(name = "stored_at") val storedAt: String
)