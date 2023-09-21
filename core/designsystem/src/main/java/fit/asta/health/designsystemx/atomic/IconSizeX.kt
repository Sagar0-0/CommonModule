package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class IconSizeX(
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 24.dp,
    val regularSize: Dp = 32.dp,
    val medium: Dp = 80.dp,
    val large: Dp = 150.dp,
)

val LocalIconSizeX = compositionLocalOf { IconSizeX() }