package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A class to model background color and tonal elevation values for Asta.
 */
@Immutable
data class BackgroundThemeX(
    val color: Color? = null,
    val tonalElevation: Dp? = null,
)

/**
 * A composition local for [BackgroundThemeX].
 */
val LocalBackgroundThemeX = staticCompositionLocalOf { BackgroundThemeX() }