package xyz.schwaab.hearthis.ui.track

import xyz.schwaab.hearthis.base.BaseViewHolder
import xyz.schwaab.hearthis.databinding.TrackItemBinding
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Track

class TrackViewHolder(
    private val imageViewLoader: ImageViewLoader,
    binding: TrackItemBinding
) : BaseViewHolder<TrackItemBinding, Track>(binding) {
    override fun composeViewWithNewData(item: Track) {
        binding.apply {
            imageViewLoader.loadImage(item.artworkUrl).into(ivTrackArt)

            tvTrackLenght.text = item.duration.seconds.toString() // TODO Format to human-readable
            tvTrackPlayCount.text = item.playbacksCount.toString()
            tvTrackTitle.text = item.title
        }
    }
}