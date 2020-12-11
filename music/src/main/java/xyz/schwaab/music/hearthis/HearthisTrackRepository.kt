package xyz.schwaab.music.hearthis

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.track.GetTracksResponse
import xyz.schwaab.music.track.TrackRepository
import java.io.IOException

class HearthisTrackRepository(private val hearthisInterface: HearthisClientInterface) :
    TrackRepository {

    override suspend fun getTracksForArtist(
        artist: Artist,
        page: Int,
        count: Int
    ): GetTracksResponse {
        return try {
            val response =
                hearthisInterface.getArtistTracks(artist.accessInfo.permalink, page, count)
            if (response.isSuccessful) {
                val tracks = response.body()!!.map { it.toTrack() }
                GetTracksResponse.Success(tracks)
            } else {
                // Bad request, internal server error, teapot, etc.
                GetTracksResponse.Failure.ServiceUnavailable
            }
        } catch (cancellation: CancellationException) {
            GetTracksResponse.Failure.Cancelled
        } catch (ioException: IOException) { // Socket, timeout, etc.
            GetTracksResponse.Failure.LackOfConnection
        } catch (exception: SerializationException) {
            GetTracksResponse.Failure.Serialization
        }
    }
}