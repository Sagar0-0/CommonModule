package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageHeightX(
    val small: Dp = 48.dp,
    val medium: Dp = 72.dp,
    val large: Dp = 180.dp,
)

internal val LocalImageHeightX = compositionLocalOf { ImageHeightX() }