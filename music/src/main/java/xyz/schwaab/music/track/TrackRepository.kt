package xyz.schwaab.music.track

import xyz.schwaab.music.model.Artist

interface TrackRepository {
    suspend fun getTracksForArtist(artist: Artist, page: Int, count: Int): GetTracksResponse
}
