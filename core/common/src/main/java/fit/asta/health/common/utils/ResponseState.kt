package fit.asta.health.common.utils

import fit.asta.health.common.R

sealed interface ResponseState<out T> {
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>
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