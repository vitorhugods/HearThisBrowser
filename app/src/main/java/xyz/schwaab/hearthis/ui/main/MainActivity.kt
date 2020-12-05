package xyz.schwaab.hearthis.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.androidx.fragment.android.replace
import xyz.schwaab.hearthis.R
import xyz.schwaab.hearthis.ui.feed.FeedFragment

class MainActivity : AppCompatActivity() {

    private val viewModel = viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace<FeedFragment>(R.id.container)
                .commitNow()
        }
    }
}