package xyz.schwaab.music.track

import xyz.schwaab.music.model.Artist

interface TrackService {
    suspend fun getTracksForArtist(artist: Artist, page: Int): GetTracksResponse

    companion object
}

class SimpleTrackService(private val trackRepository: TrackRepository) : TrackService {

    override suspend fun getTracksForArtist(artist: Artist, page: Int): GetTracksResponse {
        return trackRepository.getTracksForArtist(artist, page, PAGE_SIZE)
    }

    companion object {
        private const val PAGE_SIZE = 13
    }
}
