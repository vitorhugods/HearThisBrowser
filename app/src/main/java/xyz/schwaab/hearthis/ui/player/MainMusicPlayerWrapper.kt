package xyz.schwaab.hearthis.ui.player

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable
import xyz.schwaab.hearthis.base.BaseViewComposer
import xyz.schwaab.hearthis.databinding.PlayerViewBinding
import xyz.schwaab.image.ImageViewLoader
import kotlin.math.roundToInt

/**
 * Handles the Music Player in the main screen.
 * @param binding the PlayerViewBinding. For the moment, it assumes that such view is a direct child of a CoordinatorLayout, and has a BottomSheetBehavior
 */
class MainMusicPlayerWrapper(
    private val imageViewLoader: ImageViewLoader,
    private val binding: PlayerViewBinding
) : BaseMusicPlayer(), BaseViewComposer<PlayingTrackInfo?> {

    private val playerSheetBehavior
        get() = run {
            val bottomSheetParams =
                binding.root.layoutParams as CoordinatorLayout.LayoutParams
            bottomSheetParams.behavior as BottomSheetBehavior
        }
    private val playPauseDrawable = MaterialPlayPauseDrawable(binding.root.context)

    init {
        prepareBottomSheetPeekHeightCalculation()
        configureBottomSheetBehavior()
        configurePlayPauseButton()
    }

    private fun configurePlayPauseButton() {
        binding.playPauseButton.setOnClickListener {
            when (state) {
                MusicPlayer.State.PLAYING -> {
                    pause()
                }
                MusicPlayer.State.PAUSED -> {
                    play()
                }
            }
        }
        binding.playPauseButton.setImageDrawable(playPauseDrawable)
    }

    private fun prepareBottomSheetPeekHeightCalculation() {
        binding.motionLayout.doOnLayout {
            playerSheetBehavior.peekHeight =
                binding.containerSeekbar.run { y + height }.roundToInt()
            playerSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun configureBottomSheetBehavior() {
        binding.root.setOnClickListener {
            playerSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        hide()
        playerSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) {
                    binding.motionLayout.progress = slideOffset
                }
            }
        })
    }

    private fun hide() {
        playerSheetBehavior.isHideable = true
        playerSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun show() {
        playerSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        playerSheetBehavior.isHideable = false
    }

    override fun play() {
        super.play()
        playPauseDrawable.setState(MaterialPlayPauseDrawable.State.Pause)
    }

    override fun pause() {
        super.pause()
        playPauseDrawable.setState(MaterialPlayPauseDrawable.State.Play)
    }

    override fun onLoadingStateChange(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    override fun onProgressChanged(needlePositionInMillis: Int) {
        if (currentTrack == null) {
            binding.seekbar.value = 0f
            return
        }
        val needleInSeconds = needlePositionInMillis.toFloat() / 1000
        binding.seekbar.value = needleInSeconds.coerceAtMost(binding.seekbar.valueTo)
        binding.tvTrackCurrentTime.text = needleInSeconds.coerceAtMost(binding.seekbar.valueTo).toString() // TODO Human Readable formatting
    }

    /**
     * Attempts to collapse the player
     * @return true if the player was collapsed. False if it is not possible to collapse it any further
     */
    fun requestCollapse(): Boolean {
        return when (playerSheetBehavior.state) {
            BottomSheetBehavior.STATE_HIDDEN,
            BottomSheetBehavior.STATE_COLLAPSED -> false
            else -> {
                playerSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                true
            }
        }
    }

    override fun composeViewWithNewData(item: PlayingTrackInfo?) {
        if (item == null) {
            _state = MusicPlayer.State.PAUSED
            hide()
            return
        }
        if (currentTrack == item.track) {
            return
        }
        if (playerSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            show()
        }
        binding.playPauseButton.isEnabled = false
        loadTrack(item.track) {
            play()
            binding.playPauseButton.isEnabled = true
        }

        imageViewLoader.loadImage(item.track.artworkUrl).into(binding.ivTrackArt)
        binding.seekbar.valueTo = item.track.duration.seconds.toFloat()

        binding.tvTrackLenght.text = item.track.duration.seconds.toString()
        binding.tvArtistName.text = item.artist.name
        binding.tvTrackTitle.text = item.track.title
    }
}