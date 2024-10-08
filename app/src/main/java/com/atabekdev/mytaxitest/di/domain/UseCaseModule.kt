package com.atabekdev.mytaxitest.di.domain

import com.atabekdev.mytaxitest.domain.usecase.LocationUseCase
import com.atabekdev.mytaxitest.domain.usecase.impl.LocationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun provideLocationUseCase(impl: LocationUseCaseImpl): LocationUseCase

}