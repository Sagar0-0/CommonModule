package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

@Immutable
data class AppAlphaValues(
    val level1: Float = 0.15f,
    val level2: Float = 0.35f,
    val level3: Float = 0.5f,
    val level4: Float = 0.75f,
    val level5: Float = 1f
)

internal val LocalAppAlphaValues = compositionLocalOf { AppAlphaValues() }