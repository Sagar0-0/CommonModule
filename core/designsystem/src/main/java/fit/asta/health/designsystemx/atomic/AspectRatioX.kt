package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

/**
 * This Model class is the representation of the Local Aspect Ratio
 */
@Immutable
data class AspectRatioX(
    val square: Float = 01f / 01f,
    val fullScreen: Float = 04f / 03f,
    val wideScreen: Float = 16f / 09f,
    val common: Float = 19.5f / 09f,
)

internal val LocalAspectRatioX = compositionLocalOf { AspectRatioX() }