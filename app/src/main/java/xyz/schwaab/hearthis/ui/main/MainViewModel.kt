package xyz.schwaab.hearthis.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.schwaab.hearthis.base.BaseViewModel
import xyz.schwaab.hearthis.ui.player.PlayingTrackInfo
import xyz.schwaab.hearthis.util.LiveEvent
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.model.Track

class MainViewModel : BaseViewModel() {

    val onArtistFocusEvent = LiveEvent<Artist>()
    private val _currentTrack = MutableLiveData<PlayingTrackInfo?>(null)
    val currentlyPlaying: LiveData<PlayingTrackInfo?> = _currentTrack

    fun onArtistSelected(artist: Artist) {
        onArtistFocusEvent.postValue(artist)
    }

    fun onTrackSelected(track: Track, artist: Artist) {
        _currentTrack.postValue(PlayingTrackInfo(track, artist))
    }

    fun onMusicPlayerClose() {
        _currentTrack.postValue(null)
    }
}
