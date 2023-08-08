package fit.asta.health.common.ui.components.generic

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
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.theme.imageSize

/**[AppConstImg] is an object in the Android application that contains constants for drawable
 * resource IDs used for displaying images in different scenarios.
 */

object AppConstImg {
    val placeHolderImg = R.drawable.error_404
    val errorImg = R.drawable.error_404
}

/**The [AppDefaultIcon] function is a composable function that creates and displays an icon.
 * @param modifier the [Modifier] to be applied to this icon
 * @param imageVector [ImageVector] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents.
 * @param tint tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no tint
 * is applied.
 */

@Composable
fun AppDefaultIcon(
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

/**The [AppDefServerImg] is a custom Composable function in Jetpack Compose, used to display images
 * asynchronously.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used.
 * @param placeholder A [Painter] that is displayed while the image is loading.
 */

@Composable
fun AppDefServerImg(
    model: Any?,
    modifier: Modifier = Modifier,
    alpha: Float = 0f,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = painterResource(id = AppConstImg.placeHolderImg),
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeholder,
        alpha = alpha,
        error = painterResource(id = AppConstImg.errorImg),
    )
}

/**[AppDrawImg] is a Composable function in a Jetpack Compose application that displays an image.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content
 * @param painter to draw
 * resource in the application's resources.
 * @param contentDescription text used by accessibility services to describe what this image
 * represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used.
 */

@Composable
fun AppDrawImg(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}