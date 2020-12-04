package xyz.schwaab.hearthis.music

import xyz.schwaab.hearthis.music.model.Music

sealed class GetMusicsResponse {

    class Success(val postList: List<Music>) : GetMusicsResponse()

    sealed class Failure : GetMusicsResponse() {
        object LackOfConnection : Failure()
        object ServiceUnavailable : Failure()
        object Cancelled : Failure()
        object Serialization : Failure()
    }
}

