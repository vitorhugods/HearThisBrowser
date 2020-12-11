package xyz.schwaab.hearthis.base

sealed class UserJourneyError {

    object LackOfConnection : UserJourneyError()

    /**
     * When server has 500 different problems to take care right now,
     * or app is sending bad requests, serialization errors, etc.
     * There's absolutely no distinction between these for the user.
     */
    object LackOfService : UserJourneyError()

    sealed class Artist: UserJourneyError(){
        class NotFound(val permalink: String): Artist()
    }
}

fun interface UserJourneyErrorListener{
    fun onError(userJourneyError: UserJourneyError)
}