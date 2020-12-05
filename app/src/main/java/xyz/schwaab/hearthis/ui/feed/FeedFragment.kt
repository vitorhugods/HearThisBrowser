package xyz.schwaab.hearthis.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import xyz.schwaab.hearthis.base.BaseFragment
import xyz.schwaab.hearthis.databinding.FeedFragmentBinding
import xyz.schwaab.hearthis.ui.main.MainViewModel
import xyz.schwaab.image.ImageViewLoader

class FeedFragment : BaseFragment() {

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val feedViewModel by viewModel<FeedViewModel>()
    private val imageViewLoader: ImageViewLoader by inject()
    private val artistAdapter = ArtistAdapter(imageViewLoader)
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
            val dividerItemDecoration =
                DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)

            adapter = artistAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }
}
