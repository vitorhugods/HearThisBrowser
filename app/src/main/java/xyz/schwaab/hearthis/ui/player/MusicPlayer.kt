package xyz.schwaab.hearthis.ui.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import xyz.schwaab.music.model.Track
import java.io.IOException

interface MusicPlayer {
    fun play()
    fun pause()

    fun addMusicPlayerStateListener(stateListener: MusicPlayerStateListener)

    enum class State { PLAYING, PAUSED }
}

fun interface MusicPlayerStateListener {
    fun onPlayerStateChange(state: MusicPlayer.State)
}

abstract class BaseMusicPlayer : MusicPlayer {
    protected val currentTrack: Track?
        get() {
            return _currentTrack
        }

    private val listeners = mutableListOf<MusicPlayerStateListener>()
    protected var _state = MusicPlayer.State.PAUSED
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onPlayerStateChange(field) }
        }
    val state: MusicPlayer.State
        get() {
            return _state
        }
    private val progressUpdateHandler: Handler = Handler(Looper.getMainLooper())
    private val updateProgress: Runnable = object : Runnable {
        override fun run() {
            onProgressChanged(mediaPlayer.currentPosition)
            progressUpdateHandler.postDelayed(this, 75)
        }
    }
    private var startedUpdatingProgress = false

    override fun addMusicPlayerStateListener(stateListener: MusicPlayerStateListener) {
        if (!listeners.contains(stateListener)) {
            listeners.add(stateListener)
        }
    }

    protected fun loadTrack(track: Track, onReadyToPlay: () -> Unit) {
        if (!startedUpdatingProgress) {
            startedUpdatingProgress = true
            updateProgress.run()
        }
        if (_currentTrack == track) {
            return
        }
        _currentTrack = track
        mediaPlayer.safeAction {
            reset()
            setDataSource(track.streamUrl)
            onLoadingStateChange(true)
            prepareAsync()
            setOnPreparedListener {
                onLoadingStateChange(false)
                onReadyToPlay()
            }
        }
    }

    override fun play() {
        _state = MusicPlayer.State.PLAYING
        mediaPlayer.safeAction {
            start()
        }
    }

    override fun pause() {
        _state = MusicPlayer.State.PAUSED
        mediaPlayer.safeAction {
            pause()
        }
    }

    protected open fun onLoadingStateChange(isLoading: Boolean) {}
    protected open fun onProgressChanged(needlePositionInMillis: Int) {}
    protected open fun onLackOfConnection() {}
    protected open fun onUnknownError() {}

    private fun MediaPlayer.safeAction(action: (MediaPlayer.() -> Unit)) {
        try {
            action()
        } catch (io: IOException) {
            onLackOfConnection()
        } catch (exception: Exception) {
            //TODO Improve exception catching here
            onUnknownError()
        }
    }

    companion object {
        private var _currentTrack: Track? = null
        private val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }
}
