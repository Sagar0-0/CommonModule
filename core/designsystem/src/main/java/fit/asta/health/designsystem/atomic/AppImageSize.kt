package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppImageSize(
    val level1: Dp = 8.dp,
    val level2: Dp = 16.dp,
    val level3: Dp = 24.dp,
    val level4: Dp = 32.dp,
    val level5: Dp = 40.dp,
    val level6: Dp = 48.dp,
    val level7: Dp = 72.dp,
    val level8: Dp = 160.dp,
    val level9: Dp = 180.dp,
    val newLevel: Dp = 64.dp
)

val LocalAppImageSize = compositionLocalOf { AppImageSize() }