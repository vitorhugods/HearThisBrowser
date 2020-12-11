package xyz.schwaab.hearthis.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.replace
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import xyz.schwaab.hearthis.R
import xyz.schwaab.hearthis.base.BaseActivity
import xyz.schwaab.hearthis.databinding.MainActivityBinding
import xyz.schwaab.hearthis.ui.artist.ArtistFragment
import xyz.schwaab.hearthis.ui.feed.FeedFragment
import xyz.schwaab.hearthis.ui.formatter.TimeFormatter
import xyz.schwaab.hearthis.ui.player.MainMusicPlayerWrapper
import xyz.schwaab.hearthis.util.attachViewComposer
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Artist

class MainActivity : BaseActivity() {
    private val imageViewLoader: ImageViewLoader by inject()
    private val timeFormatter: TimeFormatter by inject()
    private lateinit var playerWrapper: MainMusicPlayerWrapper
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.includeAppbar.defaultToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            displayFeed()
        }

        viewModel.onArtistFocusEvent.observe(this, { artist ->
            displayArtistDetails(artist)
        })

        playerWrapper = MainMusicPlayerWrapper(
            imageViewLoader,
            binding.includePlayer,
            timeFormatter,
            defaultErrorListener
        )
        viewModel.currentlyPlaying.attachViewComposer(this, playerWrapper)
    }

    private fun displayFeed() {
        supportFragmentManager.beginTransaction()
            .replace<FeedFragment>(R.id.container)
            .commitNow()
    }

    private fun displayArtistDetails(artist: Artist) {
        supportFragmentManager.beginTransaction()
            .replace<ArtistFragment>(R.id.container, args = ArtistFragment.createBundle(artist))
            .addToBackStack(ArtistFragment::class.java.canonicalName)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (!playerWrapper.requestCollapse()) {
            super.onBackPressed()
        }
    }
}
