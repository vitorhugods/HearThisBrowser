package xyz.schwaab.hearthis.music

interface MusicService {
    suspend fun fetchMusics(): GetMusicsResponse
}

/**
 * A Volatile service is a service that doesn't handle any kind of storage,
 * it has a single source and doesn't store anything.
 * If it is created during different circumstances, and the used repository can't fetch data
 * an error can be expected.
 *
 * A counterpart would be a CachedService, or similarly named service.
 * That would handle persistent storage or whatever else needed
 * in order for it not to be considered Volatile
 */
class VolatileMusicService(private val musicRepository: MusicRepository) : MusicService {

    override suspend fun fetchMusics(): GetMusicsResponse {
        return musicRepository.getMusics()
    }

    companion object
}
