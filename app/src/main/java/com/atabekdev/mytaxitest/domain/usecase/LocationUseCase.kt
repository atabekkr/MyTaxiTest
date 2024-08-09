package com.atabekdev.mytaxitest.domain.usecase

import com.atabekdev.mytaxitest.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationUseCase {
    suspend fun addLocation(location: UserLocation)
    fun getLastAddedLocation(): Flow<UserLocation>
}