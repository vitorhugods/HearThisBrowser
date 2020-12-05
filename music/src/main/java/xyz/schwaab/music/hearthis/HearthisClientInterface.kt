package xyz.schwaab.music.hearthis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HearthisClientInterface {

    @GET("feed/")
    suspend fun getTrackFeed(
        @Query("type") feedType: FeedTypeDTO,
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Response<List<TrackDTO>>


    @GET("{permalink}")
    suspend fun getArtistDetails(
        @Path("permalink") permalink: String
    ): Response<ArtistDTO>

    @GET("{permalink}/")
    suspend fun getArtistTracks(
        @Path("permalink") artistPermalink: String,
        @Query("page") page: Int,
        @Query("count") count: Int,
        @Query("type") artistQueryType: String = "tracks"
    ): Response<List<TrackDTO>>

    @Serializable
    enum class FeedTypeDTO {
        @SerialName("")
        DEFAULT,

        @SerialName("new")
        NEW,

        @SerialName("popular")
        POPULAR
    }

    @Serializable
    data class TrackDTO(
        @SerialName("id") val id: String,
        @SerialName("duration") val duration: String,
        @SerialName("description") val description: String,
        @SerialName("title") val title: String,
        @SerialName("user") val user: UserDTO,
        @SerialName("genre") val readableGenre: String,
        @SerialName("background_url") val backgroundUrl: String,
        @SerialName("artwork_url") val artworkUrl: String,
        @SerialName("waveform_url") val waveformUrl: String,
        @SerialName("stream_url") val streamUrl: String,
        @SerialName("playback_count") val playbacksCount: Int,
        @Suppress("SpellCheckingInspection")
        @SerialName("favoritings_count") val favouritesCount: Int
    )

    @Serializable
    data class UserDTO(
        @SerialName("id") val id: String,
        @SerialName("permalink") val permalink: String,
        @SerialName("username") val name: String
    )

    @Serializable
    data class ArtistDTO(
        @SerialName("id") val id: String,
        @SerialName("username") val name: String,
        @SerialName("permalink") val permalink: String,
        @SerialName("avatar_url") val avatarUrl: String,
        @SerialName("background_url") val backgroundUrl: String,
        @SerialName("description") val description: String,
        @SerialName("track_count") val tracksCount: Int,
        @SerialName("likes_count") val likesCount: Int,
        @SerialName("followers_count") val followersCount: Int
    )
}