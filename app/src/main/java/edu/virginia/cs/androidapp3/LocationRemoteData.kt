package edu.virginia.cs.androidapp3

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// I used the Mars Google Codelab as an example for how to do all of this: https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet
// I also used the retrofit docs: https://square.github.io/retrofit/
// I also used the docs for kotlinx.serialization Converter: https://github.com/square/retrofit/tree/trunk/retrofit-converters/kotlinx-serialization
// I also used the kotlinx.serialization docs: https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

// Google Gemini 3 Pro taught me that I could nest Serializables like this (which makes intuitive sense, but I wanted to check)

@Serializable
data class LocationRemoteData(
    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "tag_list")
    val tagList: List<String> = listOf(),  // defensively added a fallback

    @SerialName(value = "description")
    val description: String,

    @SerialName(value = "visual_center")
    val visualCenter: VisualCenter
)

@Serializable
data class VisualCenter(
    @SerialName(value = "latitude")
    val latitude: Double,

    @SerialName(value = "longitude")
    val longitude: Double
)