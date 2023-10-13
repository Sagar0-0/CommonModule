package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppCustomSize(
    val level1: Dp = 8.dp,
    val level2: Dp = 16.dp,
    val level3: Dp = 24.dp,
    val level4: Dp = 32.dp,
    val level5: Dp = 40.dp,
    val level6: Dp = 48.dp,
    val level7: Dp = 56.dp,
    val level8: Dp = 64.dp,
    val level9: Dp = 86.dp,
    val level10: Dp = 100.dp,
    val level11: Dp = 160.dp
)

internal val LocalAppCustomSize = compositionLocalOf { AppCustomSize() }