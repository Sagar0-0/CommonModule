package fit.asta.health.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class IconSize(
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 24.dp,
    val medium: Dp = 80.dp,
    val large: Dp = 150.dp,
)

val LocalIconSize = compositionLocalOf { IconSize() }
val iconSize: IconSize
    @Composable @ReadOnlyComposable get() = LocalIconSize.current