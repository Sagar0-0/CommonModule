package fit.asta.health.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val extraMedium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val largeMedium: Dp = 40.dp,
    val extraLarge: Dp = 48.dp,
    val picSize: Dp = 160.dp,
)

val LocalImageSize = compositionLocalOf { ImageSize() }
val imageSize: ImageSize
    @Composable @ReadOnlyComposable get() = LocalImageSize.current