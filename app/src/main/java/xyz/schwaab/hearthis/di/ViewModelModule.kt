package xyz.schwaab.hearthis.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import xyz.schwaab.hearthis.ui.artist.ArtistDetailsViewModel
import xyz.schwaab.hearthis.ui.feed.FeedViewModel
import xyz.schwaab.hearthis.ui.main.MainViewModel

val viewModelsModule = module {
    viewModel { MainViewModel() }
    viewModel { FeedViewModel(get()) }
    viewModel { (permalink: String) -> ArtistDetailsViewModel(permalink, get(), get()) }
}