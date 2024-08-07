package com.atabekdev.mytaxitest.data.repository

import com.atabekdev.mytaxitest.data.dao.LocationDao
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dao: LocationDao
): LocationRepository {
    override suspend fun addLocation(location: UserLocation) {
        dao.addLocation(location)
    }

    override fun getAllLocations(): Flow<List<UserLocation>> {
        return dao.getAllLocations()
    }
}