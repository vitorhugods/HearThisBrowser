package xyz.schwaab.hearthis.ui.formatter

import android.content.Context
import xyz.schwaab.hearthis.R
import xyz.schwaab.hearthis.locale.UserLocaleProvider
import xyz.schwaab.hearthis.util.getLocalizedResources
import xyz.schwaab.music.model.Artist

interface ArtistInfoFormatter {
    fun getHumanReadableLikeCount(artist: Artist): String
    fun getHumanReadableFollowersCount(artist: Artist): String

    companion object {
        fun default(context: Context, userLocaleProvider: UserLocaleProvider): ArtistInfoFormatter =
            DefaultArtistInfoFormatter(context, userLocaleProvider)
    }
}

class DefaultArtistInfoFormatter(context: Context, userLocaleProvider: UserLocaleProvider) : ArtistInfoFormatter {
    private val resources = context.getLocalizedResources(userLocaleProvider)

    override fun getHumanReadableLikeCount(artist: Artist): String {
        return resources.getQuantityString(
            R.plurals.label_like_count,
            artist.likeCount,
            artist.likeCount
        )
    }

    override fun getHumanReadableFollowersCount(artist: Artist): String {
        return resources.getQuantityString(
            R.plurals.label_follower_count,
            artist.followerCount,
            artist.followerCount
        )
    }
}