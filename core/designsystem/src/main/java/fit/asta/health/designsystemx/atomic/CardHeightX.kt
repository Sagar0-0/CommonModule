package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CardHeightX(
    val small: Dp = 8.dp,
    val medium: Dp = 180.dp,
    val large: Dp = 252.dp,
)

internal val LocalCardHeightX = compositionLocalOf { CardHeightX() }