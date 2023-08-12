package fit.asta.health.common.utils

import retrofit2.HttpException


fun <T> T.toResponseState(): ResponseState<T?> {
    return try {
        ResponseState.Success(this)
    } catch (e: Exception) {
        e.getResponseState()
    }
}

private fun <T> Exception.getResponseState(): ResponseState<T?> {
    return when (this) {
        is HttpException -> {
            ResponseState.Success(null)
        }

        else -> {
            ResponseState.Error(this)
        }
    }
}
