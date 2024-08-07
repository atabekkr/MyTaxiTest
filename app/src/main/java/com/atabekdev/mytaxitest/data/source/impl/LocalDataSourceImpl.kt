package com.atabekdev.mytaxitest.data.source.impl

import com.atabekdev.mytaxitest.data.dao.LocationDao
import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.data.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: LocationDao
): LocalDataSource {
    override suspend fun addLocation(location: UserLocation) {
        dao.addLocation(location)
    }

    override fun getAllLocations(): Flow<List<UserLocation>> {
        return dao.getAllLocations()
    }
}