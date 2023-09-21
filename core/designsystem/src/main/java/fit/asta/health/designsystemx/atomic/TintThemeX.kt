package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model background color and tonal elevation values for Asta.
 */
@Immutable
data class TintThemeX(
    val iconTint: Color? = null,
)

/**
 * A composition local for [TintThemeX].
 */
val LocalTintThemeX = staticCompositionLocalOf { TintThemeX() }