package edu.virginia.cs.androidapp3

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

// I used the Mars Google Codelab as an example for how to do all of this: https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet
// I also used the retrofit docs: https://square.github.io/retrofit/
// I also used the docs for kotlinx.serialization Converter: https://github.com/square/retrofit/tree/trunk/retrofit-converters/kotlinx-serialization
// I also used the kotlinx.serialization docs: https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json

// Gemini 3 Pro pointed out that this could be an issue, so I read about how to fix it here:
// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md#ignoring-unknown-keys
private val customJson = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(customJson.asConverterFactory("application/json".toMediaType()))
    .baseUrl("https://www.cs.virginia.edu/~wxt4gm/")
    .build()

interface LocationApiService {
    @GET("placemarks.json")  // Gemini 3.1 Pro helped me debug an issue with my @GET
    suspend fun getLocationRemoteData(): List<LocationRemoteData>
}

object LocationApi {
    val api: LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }
}