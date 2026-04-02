package edu.virginia.cs.androidapp3

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() = runBlocking {
        val locationApi = LocationApi.api

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
                        name = tagName
                    )

                    tagList.add(tag)
                }
            }

            println(locationList)
            println(tagList)
        } catch (e: Exception) {
            // Gemini 3 Pro suggested this to ensure that I do not mistakenly suppress a coroutine's cancellation
            if (e is kotlinx.coroutines.CancellationException) {
                throw e // Let coroutines handle cancellation
            }
        }
    }
}