package fit.asta.health.common.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Idle : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>

    data class Error(val resId: Int) : UiState<Nothing>
}

@Composable
fun Int.toStringFromResId() = stringResource(this)

fun Int.toStringFromResId(context: Context) = context.getString(this)