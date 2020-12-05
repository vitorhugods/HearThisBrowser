package xyz.schwaab.hearthis.di

import com.squareup.picasso.Picasso
import org.koin.dsl.module
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.image.usingPicasso

val uiModule = module {
    single { ImageViewLoader.usingPicasso(Picasso.get()) }
}