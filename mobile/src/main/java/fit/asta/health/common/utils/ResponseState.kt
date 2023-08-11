package fit.asta.health.common.utils

sealed interface ResponseState<out T> {
    object Idle : ResponseState<Nothing>
    object Loading : ResponseState<Nothing>
    object NoInternet : ResponseState<Nothing>
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data class ErrorResponse(val code: Int) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>
}