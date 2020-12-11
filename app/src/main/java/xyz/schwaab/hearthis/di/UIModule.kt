package xyz.schwaab.hearthis.di

import com.squareup.picasso.Picasso
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module
import xyz.schwaab.hearthis.locale.UserLocaleProvider
import xyz.schwaab.hearthis.ui.artist.ArtistFragment
import xyz.schwaab.hearthis.ui.feed.FeedFragment
import xyz.schwaab.hearthis.ui.formatter.ArtistInfoFormatter
import xyz.schwaab.hearthis.ui.formatter.TimeFormatter
import xyz.schwaab.hearthis.ui.formatter.TrackInfoFormatter
import xyz.schwaab.hearthis.ui.formatter.UserJourneyErrorFormatter
import xyz.schwaab.hearthis.util.default
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.image.usingPicasso

val uiModule = module {
    single { UserLocaleProvider.default() }
    single { ImageViewLoader.usingPicasso(Picasso.get()) }
    factory { UserJourneyErrorFormatter.default(get(), get()) }
    factory { TimeFormatter.default() }
    factory { ArtistInfoFormatter.default(get(), get()) }
    factory { TrackInfoFormatter.default(get(), get()) }
    fragment { FeedFragment(get()) }
    fragment { ArtistFragment(get()) }
}