package xyz.schwaab.hearthis.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import xyz.schwaab.hearthis.BuildConfig
import xyz.schwaab.music.artist.ArtistService
import xyz.schwaab.music.hearthis.usingHearthisAPI
import xyz.schwaab.music.track.TrackService

val servicesModule = module {
    single { createOkHttpClient() }
    single { TrackService.usingHearthisAPI(get()) }
    single { ArtistService.usingHearthisAPI(get()) }
}

private fun createOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)
    }
    return builder.build()
}