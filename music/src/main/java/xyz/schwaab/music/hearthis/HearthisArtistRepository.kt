package xyz.schwaab.music.hearthis

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.Response
import xyz.schwaab.music.artist.ArtistRepository
import xyz.schwaab.music.artist.GetArtistFeedResponse
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.model.ArtistAccessInfo
import xyz.schwaab.music.model.FeedType
import java.io.IOException

class HearthisArtistRepository(private val hearthisInterface: HearthisClientInterface) :
    ArtistRepository {

    override suspend fun getArtistsFeed(
        feedType: FeedType,
        page: Int,
        count: Int
    ): GetArtistFeedResponse {
        return try {
            val trackResponse = hearthisInterface.getTrackFeed(feedType.toDTO(), page, count)
            if (trackResponse.isSuccessful) {
                getArtistsFromTrackFeedResponse(trackResponse, page)
            } else {
                // Bad request, internal server error, teapot, etc.
                GetArtistFeedResponse.Failure.ServiceUnavailable
            }
        } catch (cancellation: CancellationException) {
            GetArtistFeedResponse.Failure.Cancelled
        } catch (ioException: IOException) { // Socket, timeout, etc.
            GetArtistFeedResponse.Failure.LackOfConnection
        } catch (exception: SerializationException) {
            GetArtistFeedResponse.Failure.Serialization
        }
    }

    private suspend fun getArtistsFromTrackFeedResponse(
        trackFeedResponse: Response<List<HearthisClientInterface.TrackDTO>>,
        page: Int
    ): GetArtistFeedResponse.Success {
        //TODO Handle failures on each artists request
        val artists = trackFeedResponse.body()!!.mapNotNull { track ->
            hearthisInterface.getArtistDetails(track.user.permalink).body()?.toArtist()
        }
        return GetArtistFeedResponse.Success(page, artists)
    }

    private fun HearthisClientInterface.ArtistDTO.toArtist(): Artist = Artist(
        ArtistAccessInfo(id, permalink),
        name,
        avatarUrl,
        backgroundUrl,
        description,
        tracksCount,
        likesCount,
        followersCount
    )

    private fun FeedType.toDTO(): HearthisClientInterface.FeedTypeDTO {
        return when (this) {
            FeedType.FEATURED -> HearthisClientInterface.FeedTypeDTO.DEFAULT
            FeedType.POPULAR -> HearthisClientInterface.FeedTypeDTO.POPULAR
            FeedType.NEW -> HearthisClientInterface.FeedTypeDTO.NEW
        }
    }
}
