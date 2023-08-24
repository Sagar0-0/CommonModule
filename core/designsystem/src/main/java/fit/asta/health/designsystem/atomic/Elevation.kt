package fit.asta.health.designsystem.atomic

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Elevation(
    val extraSmall: Dp = 2.dp,
    val smallMedium: Dp = 4.dp,
    val smallExtraMedium: Dp = 6.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
)

val LocalElevation = compositionLocalOf { Elevation() }