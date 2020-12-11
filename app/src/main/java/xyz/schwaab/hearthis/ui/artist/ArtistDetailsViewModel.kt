package xyz.schwaab.hearthis.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.schwaab.hearthis.base.BaseViewModel
import xyz.schwaab.hearthis.base.UserJourneyError
import xyz.schwaab.music.artist.ArtistService
import xyz.schwaab.music.artist.GetArtistResponse
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.model.Track
import xyz.schwaab.music.track.GetTracksResponse
import xyz.schwaab.music.track.TrackService

class ArtistDetailsViewModel(
    private val artistPermalink: String,
    private val trackService: TrackService,
    private val artistService: ArtistService
) : BaseViewModel() {
    private var currentTrackPage = 0
    private val _artistDetails = MutableLiveData<Artist>()
    val artistDetails: LiveData<Artist> = _artistDetails

    private val _artistTracks = MutableLiveData<List<Track>>()
    val artistTracks: LiveData<List<Track>> = _artistTracks

    init {
        viewModelScope.launch {
            loadArtistDetails()
        }
    }

    private suspend fun loadArtistDetails() {
        when (val artistsResponse = artistService.getArtistDetails(artistPermalink)) {
            is GetArtistResponse.Success -> {
                _artistDetails.postValue(artistsResponse.artist)
                getTracksForArtist(artistsResponse)
            }
            GetArtistResponse.Failure.LackOfConnection -> postError(UserJourneyError.LackOfConnection)
            GetArtistResponse.Failure.ServiceUnavailable, GetArtistResponse.Failure.Serialization -> {
                postError(UserJourneyError.LackOfService)
            }
            GetArtistResponse.Failure.NotFound -> postError(
                UserJourneyError.Artist.NotFound(
                    artistPermalink
                )
            )
            GetArtistResponse.Failure.Cancelled -> return
        }
    }

    private suspend fun getTracksForArtist(artistsResponse: GetArtistResponse.Success) {
        val response = trackService.getTracksForArtist(artistsResponse.artist, currentTrackPage)
        when (response) {
            is GetTracksResponse.Success -> {
                _artistTracks.postValue(response.tracksList)
            }
            GetTracksResponse.Failure.LackOfConnection -> postError(UserJourneyError.LackOfConnection)
            GetTracksResponse.Failure.ServiceUnavailable, GetTracksResponse.Failure.Serialization -> {
                postError(UserJourneyError.LackOfService)
            }
            GetTracksResponse.Failure.Cancelled -> return
        }
    }
}