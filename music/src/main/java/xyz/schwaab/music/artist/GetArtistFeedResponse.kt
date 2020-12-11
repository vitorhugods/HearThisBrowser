package xyz.schwaab.music.artist

import xyz.schwaab.music.model.Artist

sealed class GetArtistFeedResponse {

    class Success(val pageNumber: Int, val artists: List<Artist>) : GetArtistFeedResponse()

    sealed class Failure : GetArtistFeedResponse() {
        object LackOfConnection : Failure()
        object ServiceUnavailable : Failure()
        object Cancelled : Failure()
        object Serialization : Failure()
    }
}
