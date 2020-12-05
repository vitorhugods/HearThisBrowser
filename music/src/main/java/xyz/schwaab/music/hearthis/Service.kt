package xyz.schwaab.music.hearthis

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import xyz.schwaab.music.artist.ArtistService
import xyz.schwaab.music.artist.SimpleArtistService
import xyz.schwaab.music.track.SimpleTrackService
import xyz.schwaab.music.track.TrackService

private const val BASE_URL = "https://api-v2.hearthis.at/"

fun ArtistService.Companion.usingHearthisAPI(
    okHttpClient: OkHttpClient,
    baseUrl: String = BASE_URL
): ArtistService {
    val hearthisInterface =
        createHearthisRetrofit(baseUrl, okHttpClient).create(HearthisClientInterface::class.java)

    val jsonPlaceholderFeedRepository = HearthisArtistRepository(hearthisInterface)
    return SimpleArtistService(jsonPlaceholderFeedRepository)
}

/**
 * @param okHttpClient will be used to perform http requests to JsonPlaceholder.
 */
fun TrackService.Companion.usingHearthisAPI(
    okHttpClient: OkHttpClient,
    baseUrl: String = BASE_URL
): TrackService {
    val retrofit = createHearthisRetrofit(baseUrl, okHttpClient)

    val hearthisInterface = retrofit.create(HearthisClientInterface::class.java)
    val repository = HearthisTrackRepository(hearthisInterface)
    return SimpleTrackService(repository)
}

private fun createHearthisRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient
): Retrofit {
    val contentType = "application/json".toMediaType()
    val jsonFormat = Json {
        ignoreUnknownKeys = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(jsonFormat.asConverterFactory(contentType))
        .build()
    return retrofit
}