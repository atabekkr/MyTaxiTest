package com.atabekdev.mytaxitest.di.data

import android.content.Context
import androidx.room.Room
import com.atabekdev.mytaxitest.data.dao.LocationDao
import com.atabekdev.mytaxitest.data.db.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): LocationDatabase {
        return Room.databaseBuilder(context, LocationDatabase::class.java, "LocationDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: LocationDatabase): LocationDao {
        return database.getLocationDao()
    }

}