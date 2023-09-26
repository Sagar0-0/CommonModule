package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This model Class contains all the data values for the default values of the elevations
 */
@Immutable
data class AppElevation(
    val noElevation: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val smallMedium: Dp = 4.dp,
    val smallExtraMedium: Dp = 6.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
)

internal val LocalAppElevation = compositionLocalOf { AppElevation() }