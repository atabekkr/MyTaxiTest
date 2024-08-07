package com.atabekdev.mytaxitest.data.repository

import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.data.source.LocalDataSource
import com.atabekdev.mytaxitest.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource,
) : LocationRepository {
    override suspend fun addLocation(location: UserLocation) {
        dataSource.addLocation(location)
    }

    override fun getAllLocations(): Flow<List<UserLocation>> {
        return dataSource.getAllLocations()
    }
}