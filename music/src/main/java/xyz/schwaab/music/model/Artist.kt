package xyz.schwaab.music.model

data class Artist(
    val accessInfo: ArtistAccessInfo,
    val name: String,
    val avatarUrl: String,
    val backgroundUrl: String,
    val description: String,
    val trackCount: Int,
    val likeCount: Int,
    val followerCount: Int
)

data class ArtistAccessInfo(val id: String, val permalink: String)
