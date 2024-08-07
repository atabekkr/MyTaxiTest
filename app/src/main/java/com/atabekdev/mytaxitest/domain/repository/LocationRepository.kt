package com.atabekdev.mytaxitest.domain.repository

import com.atabekdev.mytaxitest.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun addLocation(location: UserLocation)

    fun getAllLocations(): Flow<List<UserLocation>>

}