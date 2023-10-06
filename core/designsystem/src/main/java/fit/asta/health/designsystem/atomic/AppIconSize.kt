package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This function contains all the default Icon Sizes of the App
 */
@Immutable
data class AppIconSize(
    val level1: Dp = 8.dp,
    val level2: Dp = 16.dp,
    val level3: Dp = 24.dp,
    val level4: Dp = 32.dp,
    val level5: Dp = 40.dp,
    val level6: Dp = 80.dp,
    val level7: Dp = 150.dp
)

internal val LocalAppIconSize = compositionLocalOf { AppIconSize() }