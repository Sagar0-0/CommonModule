package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

@Immutable
data class AspectRatio(
    val square: Float = 01f / 01f,
    val fullScreen: Float = 04f / 03f,
    val wideScreen: Float = 16f / 09f,
    val common: Float = 19.5f / 09f,
)

val LocalAspectRatio = compositionLocalOf { AspectRatio() }
