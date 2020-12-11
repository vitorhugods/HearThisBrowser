package xyz.schwaab.hearthis.base

import androidx.lifecycle.ViewModel
import xyz.schwaab.hearthis.util.LiveEvent

abstract class BaseViewModel : ViewModel(){
    val errorEvents = LiveEvent<UserJourneyError>()

    protected fun postError(userJourneyError: UserJourneyError){
        errorEvents.postValue(userJourneyError)
    }
}
