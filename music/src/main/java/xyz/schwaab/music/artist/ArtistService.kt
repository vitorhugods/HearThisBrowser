package xyz.schwaab.music.artist

import xyz.schwaab.music.model.FeedType

interface ArtistService {
    suspend fun getArtistsFeed(feedType: FeedType, page: Int): GetArtistFeedResponse
    suspend fun getArtistDetails(artistPermalink: String): GetArtistResponse
    companion object
}

class SimpleArtistService(private val artistRepository: ArtistRepository) : ArtistService {

    override suspend fun getArtistsFeed(feedType: FeedType, page: Int): GetArtistFeedResponse {
        return artistRepository.getArtistsFeed(feedType, page, PAGE_SIZE)
    }

    override suspend fun getArtistDetails(artistPermalink: String): GetArtistResponse {
        return artistRepository.getArtistDetails(artistPermalink)
    }

    companion object {
        private const val PAGE_SIZE = 13
    }
}
