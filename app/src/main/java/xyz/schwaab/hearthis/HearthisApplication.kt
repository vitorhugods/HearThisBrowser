package xyz.schwaab.hearthis

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import xyz.schwaab.hearthis.di.servicesModule
import xyz.schwaab.hearthis.di.uiModule
import xyz.schwaab.hearthis.di.viewModelsModule

class HearthisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HearthisApplication)
            fragmentFactory()
            modules(servicesModule, viewModelsModule, uiModule)
        }
    }
}