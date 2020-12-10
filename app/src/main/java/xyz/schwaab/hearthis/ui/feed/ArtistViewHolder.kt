package xyz.schwaab.hearthis.ui.feed

import xyz.schwaab.hearthis.base.BaseViewHolder
import xyz.schwaab.hearthis.databinding.ArtistItemBinding
import xyz.schwaab.image.ImageViewLoader
import xyz.schwaab.music.model.Artist

class ArtistViewHolder(
    private val imageViewLoader: ImageViewLoader,
    binding: ArtistItemBinding
) :
    BaseViewHolder<ArtistItemBinding, Artist>(binding) {
    override fun composeViewWithNewData(item: Artist) {
        imageViewLoader.loadImage(item.avatarUrl)
            .into(binding.artistAvatar)

        binding.tvArtistName.text = item.name
        binding.tvLikesCount.text = item.likeCount.toString()
    }
}