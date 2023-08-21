package fit.asta.health.network.utils

sealed interface ResponseState<out T> {
    data object Idle : ResponseState<Nothing>
    data object Loading : ResponseState<Nothing>
    data object NoInternet : ResponseState<Nothing>
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data class ErrorResponse(val code: Int) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>
}