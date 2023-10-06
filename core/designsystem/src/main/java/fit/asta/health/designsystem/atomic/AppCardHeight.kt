package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppCardHeight(
    val level1: Dp = 8.dp,
    val level2: Dp = 180.dp,
    val level3: Dp = 252.dp
)

internal val LocalAppCardHeight = compositionLocalOf { AppCardHeight() }