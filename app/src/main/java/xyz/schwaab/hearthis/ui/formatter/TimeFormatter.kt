package xyz.schwaab.hearthis.ui.formatter

import android.text.format.DateUtils
import xyz.schwaab.music.model.Track

interface TimeFormatter {
    fun getHumanReadableDuration(duration: Track.Duration): String

    fun getHumanReadableDuration(seconds: Number): String {
        return getHumanReadableDuration(Track.Duration(seconds.toInt()))
    }

    companion object {
        fun default(): TimeFormatter = DefaultTimeFormatter()
    }
}

class DefaultTimeFormatter : TimeFormatter {
    override fun getHumanReadableDuration(duration: Track.Duration): String {
        return DateUtils.formatElapsedTime(duration.seconds.toLong())
    }
}

