package fit.asta.health.common.utils

import fit.asta.health.resources.strings.R

sealed interface ResponseState<out T> {
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>
}

suspend fun <T> getResponseState(request: suspend () -> T): ResponseState<T> {
    return try {
        ResponseState.Success(request())
    } catch (e: Exception) {
        ResponseState.Error(e)
    }
}


fun <T> ResponseState<T>.toUiState(): UiState<T> {
    return when (this) {
        is ResponseState.Success -> {
            UiState.Success(this.data)
        }

        is ResponseState.Error -> {
            UiState.Error(this.exception.toResIdFromException())
        }

        else -> {
            UiState.Idle
        }
    }
}

fun Exception.toResIdFromException(): Int {
    return when (this) {
        is MyException -> {
            this.resId
        }

        else -> R.string.unknown_error
    }
}