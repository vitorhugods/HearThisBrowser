package xyz.schwaab.music.track

import xyz.schwaab.music.model.Track

sealed class GetTracksResponse {

    class Success(val tracksList: List<Track>) : GetTracksResponse()

    sealed class Failure : GetTracksResponse() {
        object LackOfConnection : Failure()
        object ServiceUnavailable : Failure()
        object Cancelled : Failure()
        object Serialization : Failure()
    }
}

