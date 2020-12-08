package xyz.schwaab.hearthis.ui.track

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.schwaab.hearthis.base.BaseRecyclerAdapter
import xyz.schwaab.hearthis.databinding.TrackItemBinding
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Track

class TrackAdapter(private val imageViewLoader: ImageViewLoader) :
    BaseRecyclerAdapter<Track, TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrackItemBinding.inflate(inflater, parent, false)
        return TrackViewHolder(imageViewLoader, binding)
    }
}