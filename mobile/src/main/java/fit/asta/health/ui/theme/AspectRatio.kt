package fit.asta.health.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

//Aspect Ratio,  Card Height, Icon Size, Box Size,ImageSize, Image Height,Card Elevation, Icon Button Size, Button Size

data class AspectRatio(
    val small: Float = 04f / 03f,
    val medium: Float = 16f / 09f,
    val large: Float = 19.5f / 09f,
)

val LocalAspectRatio = compositionLocalOf { AspectRatio() }
val aspectRatio: AspectRatio
    @Composable @ReadOnlyComposable get() = LocalAspectRatio.current



