package fit.asta.health.player.jetpack_audio.domain.utils

interface Paginator<K, T> {
    suspend fun loadNextItems()
    fun reset()
}