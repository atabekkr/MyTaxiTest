package com.atabekdev.mytaxitest.di.domain

import com.atabekdev.mytaxitest.data.repository.LocationRepositoryImpl
import com.atabekdev.mytaxitest.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPlaceRepository(impl: LocationRepositoryImpl): LocationRepository

}