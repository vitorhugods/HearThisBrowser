package xyz.schwaab.hearthis.ui.feed

import xyz.schwaab.hearthis.base.BaseViewHolder
import xyz.schwaab.hearthis.databinding.ArtistItemBinding
import xyz.schwaab.hearthis.ui.formatter.ArtistInfoFormatter
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Artist

class ArtistViewHolder(
    private val imageViewLoader: ImageViewLoader,
    binding: ArtistItemBinding,
    private val artistInfoFormatter: ArtistInfoFormatter
) :
    BaseViewHolder<ArtistItemBinding, Artist>(binding) {
    override fun composeViewWithNewData(item: Artist) {
        imageViewLoader.loadImage(item.avatarUrl)
            .into(binding.artistAvatar)

        binding.tvArtistName.text = item.name
        binding.tvLikesCount.text = artistInfoFormatter.getHumanReadableLikeCount(item)
        binding.tvFollowersCount.text = artistInfoFormatter.getHumanReadableFollowersCount(item)
    }
}