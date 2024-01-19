package fit.asta.health.designsystem.molecular

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.atomic.AppLoadingScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation

@Composable
fun <T> AppUiStateHandler(
    uiState: UiState<T>,
    isScreenLoading: Boolean = true,
    onIdle: @Composable () -> Unit = {},
    onErrorMessage: () -> Unit = {},
    onRetry: () -> Unit = {},
    onSuccess: @Composable (data: T) -> Unit,
) {
    val context = LocalContext.current
    when (uiState) {

        is UiState.Success -> {
            onSuccess.invoke(uiState.data)
        }

        is UiState.Idle -> {
            onIdle.invoke()
        }

        is UiState.Loading -> {
            if (isScreenLoading) {
                AppLoadingScreen()
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }
        }

        is UiState.NoInternet -> {
            AppInternetErrorDialog {
                onRetry.invoke()
            }
        }

        is UiState.ErrorMessage -> {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    uiState.resId.toStringFromResId(context),
                    Toast.LENGTH_SHORT
                ).show()
                onErrorMessage.invoke()
            }
        }

        is UiState.ErrorRetry -> {
            AppErrorScreen(
                text = uiState.resId.toStringFromResId(context),
            ) {
                onRetry.invoke()
            }
        }

    }
}