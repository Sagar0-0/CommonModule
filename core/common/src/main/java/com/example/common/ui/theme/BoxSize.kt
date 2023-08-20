package fit.asta.health.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BoxSize(
    val small: Dp = 8.dp,
    val largeSmall: Dp = 42.dp,
    val medium: Dp = 100.dp,
    val extraMedium: Dp = 120.dp,
    val large: Dp = 200.dp,
)

val LocalBoxSize = compositionLocalOf { BoxSize() }
val boxSize: BoxSize
    @Composable @ReadOnlyComposable get() = LocalBoxSize.current