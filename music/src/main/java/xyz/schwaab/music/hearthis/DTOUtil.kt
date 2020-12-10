package xyz.schwaab.music.hearthis

import xyz.schwaab.music.model.Track

internal fun HearthisClientInterface.TrackDTO.toTrack(): Track = Track(
    id,
    Track.Duration(duration.toInt()),
    description,
    title,
    readableGenre,
    backgroundUrl,
    artworkUrl,
    waveformUrl,
    streamUrl,
    playbacksCount,
    favouritesCount
)