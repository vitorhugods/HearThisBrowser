package xyz.schwaab.hearthis.ui.formatter

import android.content.Context
import xyz.schwaab.hearthis.R
import xyz.schwaab.hearthis.locale.UserLocaleProvider
import xyz.schwaab.hearthis.util.getLocalizedResources
import xyz.schwaab.music.model.Track

interface TrackInfoFormatter {
    fun getHumanReadablePlayCount(track: Track): String
    fun getHumanReadableFavouriteCount(track: Track): String

    companion object {
        fun default(context: Context, userLocaleProvider: UserLocaleProvider): TrackInfoFormatter =
            DefaultTrackInfoFormatter(context, userLocaleProvider)
    }
}

class DefaultTrackInfoFormatter(
    context: Context,
    userLocaleProvider: UserLocaleProvider
) : TrackInfoFormatter {
    private val resources = context.getLocalizedResources(userLocaleProvider)

    override fun getHumanReadablePlayCount(track: Track): String {
        return resources.getQuantityString(
            R.plurals.label_playback_count,
            track.playbacksCount,
            track.playbacksCount
        )
    }

    override fun getHumanReadableFavouriteCount(track: Track): String {
        return resources.getQuantityString(
            R.plurals.label_playback_count,
            track.favouritesCount,
            track.favouritesCount
        )
    }
}