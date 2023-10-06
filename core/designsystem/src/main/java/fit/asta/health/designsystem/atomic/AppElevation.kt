package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This model Class contains all the data values for the default values of the elevations
 */
@Immutable
data class AppElevation(
    val level0: Dp = 0.dp,
    val level1: Dp = 2.dp,
    val level2: Dp = 4.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 16.dp,
    val level6: Dp = 32.dp
)

internal val LocalAppElevation = compositionLocalOf { AppElevation() }