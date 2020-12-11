package xyz.schwaab.hearthis.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.schwaab.hearthis.base.BaseRecyclerAdapter
import xyz.schwaab.hearthis.databinding.ArtistItemBinding
import xyz.schwaab.hearthis.ui.formatter.ArtistInfoFormatter
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Artist

class ArtistAdapter(
    private val imageViewLoader: ImageViewLoader,
    private val artistInfoFormatter: ArtistInfoFormatter
) :
    BaseRecyclerAdapter<Artist, ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArtistItemBinding.inflate(inflater, parent, false)
        return ArtistViewHolder(imageViewLoader, binding, artistInfoFormatter)
    }
}