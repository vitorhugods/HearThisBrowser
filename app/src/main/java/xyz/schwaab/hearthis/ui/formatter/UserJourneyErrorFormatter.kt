package xyz.schwaab.hearthis.ui.formatter

import android.content.Context
import xyz.schwaab.hearthis.R
import xyz.schwaab.hearthis.base.UserJourneyError
import xyz.schwaab.hearthis.locale.UserLocaleProvider
import xyz.schwaab.hearthis.util.getLocalizedResources

interface UserJourneyErrorFormatter {
    fun getReadableMessageForError(userJourneyError: UserJourneyError): String

    companion object{
        fun default(context: Context, userLocaleProvider: UserLocaleProvider): UserJourneyErrorFormatter{
            return DefaultUserJourneyErrorFormatter(context, userLocaleProvider)
        }
    }
}

class DefaultUserJourneyErrorFormatter(context: Context, userLocaleProvider: UserLocaleProvider): UserJourneyErrorFormatter{
    private val resources = context.getLocalizedResources(userLocaleProvider)

    override fun getReadableMessageForError(userJourneyError: UserJourneyError): String {
        return when(userJourneyError){
            UserJourneyError.LackOfConnection -> resources.getString(R.string.error_lack_of_connection)
            UserJourneyError.LackOfService -> resources.getString(R.string.error_lack_of_service)
            UserJourneyError.LackOfService -> resources.getString(R.string.error_lack_of_service)
            is UserJourneyError.Artist.NotFound -> resources.getString(R.string.error_artist_not_found)
        }
    }
}