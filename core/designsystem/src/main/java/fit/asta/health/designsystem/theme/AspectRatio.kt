package fit.asta.health.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

data class AspectRatio(
    val square: Float = 01f / 01f,
    val fullScreen: Float = 04f / 03f,
    val wideScreen: Float = 16f / 09f,
    val common: Float = 19.5f / 09f,
)

val LocalAspectRatio = compositionLocalOf { AspectRatio() }

val aspectRatio: AspectRatio
    @Composable @ReadOnlyComposable get() = LocalAspectRatio.current



