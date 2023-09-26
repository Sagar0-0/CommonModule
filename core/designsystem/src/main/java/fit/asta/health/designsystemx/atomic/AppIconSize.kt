package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This function contains all the default Icon Sizes of the App
 */
@Immutable
data class AppIconSize(
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 24.dp,
    val regularSize: Dp = 32.dp,
    val medium: Dp = 80.dp,
    val large: Dp = 150.dp,
)

internal val LocalAppIconSize = compositionLocalOf { AppIconSize() }