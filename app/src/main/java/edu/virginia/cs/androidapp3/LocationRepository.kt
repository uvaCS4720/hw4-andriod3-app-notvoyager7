package edu.virginia.cs.androidapp3

import kotlinx.coroutines.flow.Flow

// Gemini 3 Pro generated this RefreshResult enum for me
sealed interface RefreshResult {
    data object SUCCESS : RefreshResult
    data object ERROR : RefreshResult
}

class LocationRepository(
    private val locationDao: LocationDao,
    private val locationApi: LocationApiService
) {
    fun getLocationsWithTags(): Flow<List<LocationWithTags>> {
        return locationDao.getLocationsWithTags()
    }

    fun getUniqueTags(): Flow<List<String>> {
        return locationDao.getUniqueTags()
    }

    suspend fun refreshLocationsWithTags(): RefreshResult {
        try {
            val locationsRemoteData: List<LocationRemoteData> = locationApi.getLocationsRemoteData()

            val locationList: MutableList<Location> = mutableListOf()
            val tagList: MutableList<Tag> = mutableListOf()

            for (locationRemoteData in locationsRemoteData) {

                // Strip fields from raw remote data
                val id = locationRemoteData.id
                val name = locationRemoteData.name
                val tagNameList = locationRemoteData.tagList
                val description = locationRemoteData.description
                val longitude = locationRemoteData.visualCenter.longitude
                val latitude = locationRemoteData.visualCenter.latitude

                // create Location database object and append to list for bulk insertion
                val location = Location(
                    id = id,
                    name = name,
                    description = description,
                    latitude = latitude,
                    longitude = longitude
                )

                locationList.add(location)

                for (tagName in tagNameList) {
                    val tag = Tag(
                        locationId = id,
                        tag = tagName
                    )

                    tagList.add(tag)
                }
            }

            locationDao.synchronizeLocationsAndTags(locationList, tagList)
        } catch (e: Exception) {
            // Gemini 3 Pro suggested this to ensure that I do not mistakenly suppress a coroutine's cancellation
            if (e is kotlinx.coroutines.CancellationException) {
                throw e // Let coroutines handle cancellation
            }

            return RefreshResult.ERROR
        }

        return RefreshResult.SUCCESS
    }
}