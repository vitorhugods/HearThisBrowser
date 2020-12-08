package xyz.schwaab.hearthis.ui.main

import xyz.schwaab.hearthis.base.BaseViewModel
import xyz.schwaab.hearthis.util.LiveEvent
import xyz.schwaab.music.model.Artist
import xyz.schwaab.music.model.Track

class MainViewModel : BaseViewModel() {

    val onArtistFocusEvent = LiveEvent<Artist>()

    fun onArtistSelected(artist: Artist) {
        onArtistFocusEvent.postValue(artist)
    }

    fun onTrackSelected(track: Track){
        //TODO let's play
    }
}