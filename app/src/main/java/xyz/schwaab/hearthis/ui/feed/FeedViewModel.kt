package xyz.schwaab.hearthis.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.schwaab.hearthis.base.BaseViewModel
import xyz.schwaab.hearthis.base.UserJourneyError
import xyz.schwaab.music.artist.ArtistService
import xyz.schwaab.music.artist.GetArtistFeedResponse
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.model.FeedType

class FeedViewModel(private val artistService: ArtistService) : BaseViewModel() {

    var currentPage = 0

    private val _artistList = MutableLiveData<List<Artist>>()
    val artistList: LiveData<List<Artist>> = _artistList

    fun loadInitialData() {
        viewModelScope.launch {
            loadArtistPage(currentPage)
        }
    }

    private fun loadArtistPage(page: Int) {
        viewModelScope.launch {
            when (val feed = artistService.getArtistsFeed(FeedType.POPULAR, page)) {
                is GetArtistFeedResponse.Success -> {
                    _artistList.postValue(feed.artists)
                }
                GetArtistFeedResponse.Failure.LackOfConnection -> postError(UserJourneyError.LackOfConnection)
                GetArtistFeedResponse.Failure.ServiceUnavailable,
                GetArtistFeedResponse.Failure.Serialization -> postError(
                    UserJourneyError.LackOfService
                )
                GetArtistFeedResponse.Failure.Cancelled -> return@launch
            }
        }
    }
}
