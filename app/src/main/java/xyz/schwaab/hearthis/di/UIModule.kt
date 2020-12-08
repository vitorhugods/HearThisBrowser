package xyz.schwaab.hearthis.di

import com.squareup.picasso.Picasso
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module
import xyz.schwaab.hearthis.ui.artist.ArtistFragment
import xyz.schwaab.hearthis.ui.feed.FeedFragment
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.image.usingPicasso

val uiModule = module {
    single { ImageViewLoader.usingPicasso(Picasso.get()) }
    fragment { FeedFragment() }
    fragment { ArtistFragment(get()) }
}