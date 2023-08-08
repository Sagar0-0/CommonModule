package fit.asta.health.common.ui.components.generic

import java.io.IOException

sealed class AppState< T>(
    val data: T? = null,
    val error: Throwable? = null,
    val networkError: IOException? = null
) {
    class Loading<T> : AppState<T>()
    class Empty<T> : AppState<T>()
    class Error<T>(error: Throwable) : AppState<T>(error=error)
    class NetworkError<T>(networkError: IOException) : AppState<T>(networkError=networkError)
    class Success<T>(data: T) : AppState<T>(data)
}
