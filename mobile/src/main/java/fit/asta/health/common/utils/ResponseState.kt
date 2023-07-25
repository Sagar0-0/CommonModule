package fit.asta.health.common.utils

sealed class ResponseState<out T> {
    object Idle : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
    object NoInternet : ResponseState<Nothing>()
    data class Error(val error: Throwable) : ResponseState<Nothing>()
    data class Success<R>(val data: R) : ResponseState<R>()
}