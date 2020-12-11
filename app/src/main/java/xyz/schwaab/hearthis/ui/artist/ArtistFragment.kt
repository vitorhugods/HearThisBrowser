package xyz.schwaab.hearthis.ui.artist

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.schwaab.hearthis.base.BaseFragment
import xyz.schwaab.hearthis.databinding.ArtistDetailsFragmentBinding
import xyz.schwaab.hearthis.ui.main.MainViewModel
import xyz.schwaab.hearthis.ui.track.TrackAdapter
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Artist
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ArtistFragment(private val imageViewLoader: ImageViewLoader) : BaseFragment() {
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val viewModel: ArtistDetailsViewModel by viewModel {
        parametersOf(
            getArtistPermalinkFromBundle(requireArguments())
        )
    }
    private lateinit var binding: ArtistDetailsFragmentBinding
    private val trackAdapter = TrackAdapter(get(), get(), get()).apply {
        onItemClick { item, _, _ ->
            mainViewModel.onTrackSelected(item, viewModel.artistDetails.value!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArtistDetailsFragmentBinding.inflate(inflater, container, false)

        viewModel.artistDetails.observe(viewLifecycleOwner, { artist ->
            imageViewLoader.loadImage(artist.backgroundUrl).into(binding.ivArtistBackground)
            imageViewLoader.loadImage(artist.avatarUrl).into(binding.ivArtistAvatar)

            binding.tvArtistName.text = artist.name
        })

        binding.tracksList.adapter = trackAdapter
        viewModel.artistTracks.observe(viewLifecycleOwner, { tracks ->
            trackAdapter.data = tracks
        })

        return binding.root
    }

    companion object {
        private const val BUNDLE_KEY = "ParametersBundleKey"

        fun createBundle(artist: Artist): Bundle {
            return Bundle().apply {
                putParcelable(BUNDLE_KEY, Parameters(artist.accessInfo.permalink))
            }
        }

        fun getArtistPermalinkFromBundle(bundle: Bundle): String {
            return (bundle[BUNDLE_KEY] as Parameters).artistPermalink
        }
    }

    @Parcelize
    class Parameters(val artistPermalink: String) : Parcelable

}