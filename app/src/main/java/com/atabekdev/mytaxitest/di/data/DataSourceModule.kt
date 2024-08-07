package com.atabekdev.mytaxitest.di.data

import com.atabekdev.mytaxitest.data.source.LocalDataSource
import com.atabekdev.mytaxitest.data.source.impl.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

}