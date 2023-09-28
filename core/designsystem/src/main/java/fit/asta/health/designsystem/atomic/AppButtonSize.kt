package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppButtonSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 53.dp,
)

internal val LocalAppButtonSize = compositionLocalOf { AppButtonSize() }