package com.atabekdev.mytaxitest.domain.usecase.impl

import com.atabekdev.mytaxitest.data.models.UserLocation
import com.atabekdev.mytaxitest.domain.repository.LocationRepository
import com.atabekdev.mytaxitest.domain.usecase.LocationUseCase
import javax.inject.Inject

class LocationUseCaseImpl @Inject constructor(
    private val repository: LocationRepository,
) : LocationUseCase {

    override suspend fun addLocation(location: UserLocation) {
        repository.addLocation(location)
    }

    override fun getLastAddedLocation() = repository.getLastAddedLocation()

}