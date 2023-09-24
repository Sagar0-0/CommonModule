package fit.asta.health.designsystemx.molecular.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.designsystem.theme.imageSize


/**
 * The [AstaLocalImage] function is a composable function that creates and displays an icon.
 *
 * @param modifier the [Modifier] to be applied to this icon
 * @param imageVector [ImageVector] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents.
 * @param tint tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no tint
 * is applied.
 */
@Composable
fun AstaLocalImage(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier.size(imageSize.standard)
    )
}


/**
 * [AstaLocalImage] is a Composable function in a Jetpack Compose application that displays an image.
 *
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content
 * @param painter to draw
 * resource in the application's resources.
 * @param contentDescription text used by accessibility services to describe what this image
 * represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used.
 */
@Composable
fun AstaLocalImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}