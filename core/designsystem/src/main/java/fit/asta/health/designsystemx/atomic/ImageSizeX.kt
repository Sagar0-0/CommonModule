package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageSizeX(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val standard: Dp = 24.dp,
    val large: Dp = 32.dp,
    val largeMedium: Dp = 40.dp,
    val extraLarge: Dp = 48.dp,
    val picSize: Dp = 160.dp,
)

val LocalImageSizeX = compositionLocalOf { ImageSizeX() }