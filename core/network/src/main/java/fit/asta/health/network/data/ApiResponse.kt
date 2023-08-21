package fit.asta.health.network.data

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class HttpError<T>(val code: Int, val msg: String, val ex: Exception) : ApiResponse<T>()
    data class Error<T>(val exception: Exception) : ApiResponse<T>()
}