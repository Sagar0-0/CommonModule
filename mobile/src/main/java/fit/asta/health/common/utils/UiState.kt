package fit.asta.health.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    object Idle : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>

    data class Error(val resId: Int) : UiState<Nothing>
}

@Composable
fun Int.toStringRes() = stringResource(this)