package fit.asta.health.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

data class AspectRatio(
    val original: Float = 01f / 01f,
    val small: Float = 04f / 03f,
    val medium: Float = 16f / 09f,
    val large: Float = 19.5f / 09f,
)

val LocalAspectRatio = compositionLocalOf { AspectRatio() }
val aspectRatio: AspectRatio
    @Composable @ReadOnlyComposable get() = LocalAspectRatio.current



