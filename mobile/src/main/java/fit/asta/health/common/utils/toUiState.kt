package fit.asta.health.common.utils

import fit.asta.health.R

fun <T> ResponseState<T>.toUiState(): UiState<T> {
    return when (this) {
        is ResponseState.Success -> {
            UiState.Success(this.data)
        }

        is ResponseState.Error -> {
            UiState.Error(this.exception.toStringResId())
        }

        else -> {
            UiState.Idle
        }
    }
}

fun Exception.toStringResId(): Int {
    return when (this) {
        is MyException->{
            this.resId
        }
        else -> R.string.unknown_error
    }
}