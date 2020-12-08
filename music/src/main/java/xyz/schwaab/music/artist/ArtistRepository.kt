package xyz.schwaab.music.artist

import xyz.schwaab.music.model.FeedType

interface ArtistRepository {
    suspend fun getArtistsFeed(feedType: FeedType, page: Int, count: Int): GetArtistFeedResponse
    suspend fun getArtistDetails(permalink: String): GetArtistResponse
}