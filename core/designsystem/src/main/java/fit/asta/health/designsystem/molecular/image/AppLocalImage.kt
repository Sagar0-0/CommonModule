package fit.asta.health.designsystem.molecular.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.designsystem.AppTheme


/**
 * The [AppLocalImage] function is a composable function that creates and displays an icon.
 *
 * @param modifier the [Modifier] to be applied to this icon
 * @param imageVector [ImageVector] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used.
 * @param colorFilter Optional colorFilter to apply for the [Painter] when it is rendered onscreen
 */
@Composable
fun AppLocalImage(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier.size(AppTheme.imageSize.level3),
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}


/**
 * [AppLocalImage] is a Composable function in a Jetpack Compose application that displays an image.
 *
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content
 * @param painter to draw
 * resource in the application's resources.
 * @param contentDescription text used by accessibility services to describe what this image
 * represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used.
 * @param colorFilter Optional colorFilter to apply for the [Painter] when it is rendered onscreen
 */
@Composable
fun AppLocalImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}