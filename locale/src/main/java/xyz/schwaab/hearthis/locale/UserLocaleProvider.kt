package xyz.schwaab.hearthis.locale

import java.util.*

interface UserLocaleProvider {
    fun getLocale(): Locale

    companion object
}
