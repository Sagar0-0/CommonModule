package fit.asta.health.scheduler.util

sealed class SpotifyNetworkCall<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initialized<T> : SpotifyNetworkCall<T>()
    class Loading<T> : SpotifyNetworkCall<T>()
    class Success<T>(data: T) : SpotifyNetworkCall<T>(data)
    class Failure<T>(data: T? = null, message: String?) : SpotifyNetworkCall<T>(data, message)
}
