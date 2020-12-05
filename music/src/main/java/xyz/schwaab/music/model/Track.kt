package xyz.schwaab.music.model

data class Track(
    val id: String,
    val duration: String,
    val description: String,
    val title: String,
    val readableGenre: String,
    val backgroundUrl: String,
    val artworkUrl: String,
    val waveformUrl: String,
    val streamUrl: String,
    val playbacksCount: Int,
    val favouritesCount: Int
)
