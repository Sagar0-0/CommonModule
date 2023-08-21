package fit.asta.health.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CardElevation(
    val extraSmall: Dp = 2.dp,
    val smallMedium: Dp = 4.dp,
    val smallExtraMedium: Dp = 6.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
)

val LocalCardElevation = compositionLocalOf { CardElevation() }
val cardElevation: CardElevation
    @Composable @ReadOnlyComposable get() = LocalCardElevation.current