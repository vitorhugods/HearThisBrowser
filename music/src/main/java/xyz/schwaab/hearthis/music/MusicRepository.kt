package xyz.schwaab.hearthis.music

interface MusicRepository {
    suspend fun getMusics(): GetMusicsResponse
}