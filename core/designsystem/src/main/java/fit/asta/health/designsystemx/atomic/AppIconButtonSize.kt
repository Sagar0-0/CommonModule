package fit.asta.health.designsystemx.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppIconButtonSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val extraMedium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge1: Dp = 42.dp,
    val extraLarge2: Dp = 53.dp,
)

internal val LocalAppIconButtonSize = compositionLocalOf { AppIconButtonSize() }