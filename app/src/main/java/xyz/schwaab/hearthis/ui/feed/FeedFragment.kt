package xyz.schwaab.hearthis.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import xyz.schwaab.hearthis.base.BaseFragment
import xyz.schwaab.hearthis.databinding.FeedFragmentBinding
import xyz.schwaab.hearthis.ui.main.MainViewModel
import xyz.schwaab.image.ImageViewLoader

class FeedFragment(imageViewLoader: ImageViewLoader) : BaseFragment() {
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val feedViewModel by viewModel<FeedViewModel>()
    private val artistAdapter = ArtistAdapter(imageViewLoader, get())
    lateinit var binding: FeedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeedFragmentBinding.inflate(inflater, container, false)
        feedViewModel.loadInitialData()
        feedViewModel.artistList.observe(viewLifecycleOwner, { artists ->
            artistAdapter.data = artists
        })
        binding.rvArtists.apply {
            artistAdapter.onItemClick { item, _, _ ->
                mainViewModel.onArtistSelected(item)
            }
            val dividerItemDecoration =
                DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)

            adapter = artistAdapter
        }
        observeErrors(feedViewModel)
        return binding.root
    }

}
