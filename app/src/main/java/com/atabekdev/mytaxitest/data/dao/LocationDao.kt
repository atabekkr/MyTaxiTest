package com.atabekdev.mytaxitest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atabekdev.mytaxitest.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: UserLocation)

    @Query("SELECT * FROM userLocation ORDER BY stored_at DESC LIMIT 1")
    fun getLastAddedLocation(): Flow<UserLocation>

}