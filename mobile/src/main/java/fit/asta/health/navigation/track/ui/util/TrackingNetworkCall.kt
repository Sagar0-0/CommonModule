package fit.asta.health.navigation.track.ui.util

sealed class TrackingNetworkCall<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initialized<T> : TrackingNetworkCall<T>()
    class Loading<T> : TrackingNetworkCall<T>()
    class Success<T>(data: T) : TrackingNetworkCall<T>(data)
    class Failure<T>(data: T? = null, message: String?) : TrackingNetworkCall<T>(data, message)
}