package com.atabekdev.mytaxitest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atabekdev.mytaxitest.data.dao.LocationDao
import com.atabekdev.mytaxitest.data.models.UserLocation

@Database(entities = [UserLocation::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}