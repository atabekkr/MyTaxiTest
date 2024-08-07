package com.atabekdev.mytaxitest.domain.usecase

import com.atabekdev.mytaxitest.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface AddLocationUseCase {
    suspend fun addLocation(location: UserLocation)
    fun getAllLocations(): Flow<UserLocation>
}