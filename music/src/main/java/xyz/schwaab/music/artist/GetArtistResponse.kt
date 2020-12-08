package xyz.schwaab.music.artist

import xyz.schwaab.music.model.Artist

sealed class GetArtistResponse {

    class Success(val artist: Artist) : GetArtistResponse()

    sealed class Failure : GetArtistResponse() {
        object LackOfConnection : Failure()
        object ServiceUnavailable : Failure()
        object Cancelled : Failure()
        object NotFound: Failure()
        object Serialization : Failure()
    }
}
