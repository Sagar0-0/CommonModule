package com.example.common.utils

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