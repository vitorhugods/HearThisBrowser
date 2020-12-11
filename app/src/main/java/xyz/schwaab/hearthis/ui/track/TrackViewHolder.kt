package xyz.schwaab.hearthis.ui.track

import xyz.schwaab.hearthis.base.BaseViewHolder
import xyz.schwaab.hearthis.databinding.TrackItemBinding
import xyz.schwaab.hearthis.ui.formatter.TimeFormatter
import xyz.schwaab.hearthis.ui.formatter.TrackInfoFormatter
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Track

class TrackViewHolder(
    private val imageViewLoader: ImageViewLoader,
    binding: TrackItemBinding,
    private val timeFormatter: TimeFormatter,
    private val trackInfoFormatter: TrackInfoFormatter
) : BaseViewHolder<TrackItemBinding, Track>(binding) {
    override fun composeViewWithNewData(item: Track) {
        binding.apply {
            imageViewLoader.loadImage(item.artworkUrl).into(ivTrackArt)

            tvTrackLenght.text = timeFormatter.getHumanReadableDuration(item.duration)
            tvTrackPlayCount.text = trackInfoFormatter.getHumanReadablePlayCount(item)
            tvTrackTitle.text = item.title
        }
    }
}