package com.atabekdev.mytaxitest.domain.usecase

import com.atabekdev.mytaxitest.data.models.LatLngPlace

class AddLocationUseCase {
    suspend fun addLocation(location: LatLngPlace) {}
}