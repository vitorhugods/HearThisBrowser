package xyz.schwaab.hearthis.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.schwaab.hearthis.base.BaseViewModel
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
            when (val feed = artistService.getArtistsFeed(FeedType.FEATURED, page)) {
                is GetArtistFeedResponse.Success -> {
                    _artistList.postValue(feed.artists)
                }
                GetArtistFeedResponse.Failure.LackOfConnection -> TODO()
                GetArtistFeedResponse.Failure.ServiceUnavailable -> TODO()
                GetArtistFeedResponse.Failure.Cancelled -> TODO()
                GetArtistFeedResponse.Failure.Serialization -> TODO()
            }
        }
    }
}
