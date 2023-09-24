package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This Model class contains all the Spacing default values to be used in the App
 */
@Immutable
data class SpacingX(
    val minSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val extraSmall1: Dp = 7.5.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val extraMedium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 48.dp,
    val extraLarge2: Dp = 64.dp,
    val extraLarge3: Dp = 86.dp,
)

internal val LocalSpacingX = compositionLocalOf { SpacingX() }


