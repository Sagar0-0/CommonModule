package fit.asta.health.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ImageHeight(
    val small: Dp = 48.dp,
    val medium: Dp = 72.dp,
    val large: Dp = 180.dp,
)

val LocalImageHeight = compositionLocalOf { ImageHeight() }
val imageHeight: ImageHeight
    @Composable @ReadOnlyComposable get() = LocalImageHeight.current