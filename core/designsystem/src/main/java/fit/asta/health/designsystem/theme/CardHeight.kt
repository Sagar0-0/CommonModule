package fit.asta.health.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CardHeight(
    val small: Dp = 8.dp,
    val medium: Dp = 180.dp,
    val large: Dp = 252.dp,
)

val LocalCardHeight = compositionLocalOf { CardHeight() }
val cardHeight: CardHeight
    @Composable @ReadOnlyComposable get() = LocalCardHeight.current