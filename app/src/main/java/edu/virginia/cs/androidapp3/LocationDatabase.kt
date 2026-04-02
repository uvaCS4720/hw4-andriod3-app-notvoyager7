package edu.virginia.cs.androidapp3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Location::class, Tag::class], version = 1)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao

    // copied this singleton pattern from Professor McBurney's example in the Counters Lab
    companion object {
        private var instance: LocationDatabase? = null

        fun getDatabase(context: Context): LocationDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, // the application context
                    LocationDatabase::class.java, // the datatype of our database class
                    "location_database.db" // the SQLite filename
                ).build().also { instance = it }
            }
        }
    }
}