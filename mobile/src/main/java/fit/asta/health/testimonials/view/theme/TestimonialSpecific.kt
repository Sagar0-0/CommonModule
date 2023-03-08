package fit.asta.health.testimonials.view.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.R

//Aspect Ratio,  Card Height, Icon Size, Box Size,ImageSize, Image Height,Card Elevation, Icon Button Size, Button Size

data class AspectRatio(
    val small: Float = 04f / 03f,
    val medium: Float = 16f / 09f,
    val large: Float = 19.5f / 09f,
)

val LocalAspectRatio = compositionLocalOf { AspectRatio() }
val aspectRatio: AspectRatio
    @Composable @ReadOnlyComposable get() = LocalAspectRatio.current

@Preview
@Composable
fun AspectRatioDemo() {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.weatherimage),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(ratio = aspectRatio.medium)
                .fillMaxWidth()
        )

    }
}

data class CardHeight(
    val small: Dp = 8.dp,
    val medium: Dp = 180.dp,
    val large: Dp = 252.dp,
)

val LocalCardHeight = compositionLocalOf { CardHeight() }
val cardHeight: CardHeight
    @Composable @ReadOnlyComposable get() = LocalCardHeight.current


data class IconSize(
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 24.dp,
    val medium: Dp = 80.dp,
    val large: Dp = 150.dp,
)

val LocalIconSize = compositionLocalOf { IconSize() }
val iconSize: IconSize
    @Composable @ReadOnlyComposable get() = LocalIconSize.current


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


data class ImageSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val extraMedium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 48.dp,
)

val LocalImageSize = compositionLocalOf { ImageSize() }
val imageSize: ImageSize
    @Composable @ReadOnlyComposable get() = LocalImageSize.current


data class ImageHeight(
    val small: Dp = 48.dp,
    val medium: Dp = 72.dp,
    val large: Dp = 180.dp,
)

val LocalImageHeight = compositionLocalOf { ImageHeight() }
val imageHeight: ImageHeight
    @Composable @ReadOnlyComposable get() = LocalImageHeight.current


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


data class IconButtonSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val extraMedium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge1: Dp = 42.dp,
    val extraLarge2: Dp = 53.dp,
)

val LocalIconButtonSize = compositionLocalOf { IconButtonSize() }
val iconButtonSize: IconButtonSize
    @Composable @ReadOnlyComposable get() = LocalIconButtonSize.current


data class ButtonSize(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 53.dp,
)

val LocalButtonSize = compositionLocalOf { ButtonSize() }
val buttonSize: ButtonSize
    @Composable @ReadOnlyComposable get() = LocalButtonSize.current