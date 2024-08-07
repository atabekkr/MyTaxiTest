package com.atabekdev.mytaxitest.data.source

import com.atabekdev.mytaxitest.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun addLocation(location: UserLocation)
    fun getAllLocations(): Flow<List<UserLocation>>

}