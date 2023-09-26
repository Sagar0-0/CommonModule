package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BoxSizeX(
    val small: Dp = 8.dp,
    val largeSmall: Dp = 42.dp,
    val medium: Dp = 100.dp,
    val extraMedium: Dp = 120.dp,
    val large: Dp = 200.dp,
)

internal val LocalBoxSizeX = compositionLocalOf { BoxSizeX() }