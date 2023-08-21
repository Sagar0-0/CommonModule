package fit.asta.health.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Elevation(
    val low: Dp = 4.dp,
    val medium: Dp = 6.dp,
    val high: Dp = 8.dp,
)

val LocalElevation = compositionLocalOf { Elevation() }

val elevation: Elevation
    @Composable @ReadOnlyComposable get() = LocalElevation.current