package fit.asta.health.tools.sleep.utils

sealed class SleepNetworkCall<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initialized<T> : SleepNetworkCall<T>()
    class Loading<T> : SleepNetworkCall<T>()
    class Success<T>(data: T) : SleepNetworkCall<T>(data)
    class Failure<T>(data: T? = null, message: String?) : SleepNetworkCall<T>(data, message)
}