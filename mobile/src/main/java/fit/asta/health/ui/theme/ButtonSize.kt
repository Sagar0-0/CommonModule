package fit.asta.health.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ButtonSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 53.dp,
)

val LocalButtonSize = compositionLocalOf { ButtonSize() }
val buttonSize: ButtonSize
    @Composable @ReadOnlyComposable get() = LocalButtonSize.current