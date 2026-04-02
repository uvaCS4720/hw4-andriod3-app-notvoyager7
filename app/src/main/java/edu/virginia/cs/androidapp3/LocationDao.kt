package edu.virginia.cs.androidapp3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    // Gemini 3 Pro suggested adding this for batch inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocations(locations: List<Location>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)  // Gemini 3.1 Pro suggested IGNORE instead of replace for efficiency
    suspend fun insertAllTags(tags: List<Tag>)

    @Transaction
    @Query("SELECT * FROM Location")
    fun getLocationsWithTags(): Flow<List<LocationWithTags>>

    // because of the on-delete cascade, this will delete all tags as well
    @Query("DELETE FROM Location")
    fun deleteAllLocations()
}