package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.theme.imageSize

/**[AppConstImg] is an object in the Android application that contains constants for drawable
 * resource IDs used for displaying images in different scenarios.
 * */

object AppConstImg {
    val placeHolderImg = R.drawable.error_404
    val errorImg = R.drawable.error_404
}

/**The [AppDefaultIcon] function is a composable function that creates and displays an icon using the
 *  Jetpack Compose library. It simplifies the process of rendering an icon with default settings
 *  and provides the flexibility to customize its appearance.
 * @param imageVector [ImageVector] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents.
 * @param modifier the [Modifier] to be applied to this icon
 * @param tint tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no tint
 * is applied.
 * */

@Composable
fun AppDefaultIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
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
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used.
 * */

@Composable
fun AppDefServerImg(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = painterResource(id = AppConstImg.placeHolderImg),
        error = painterResource(id = AppConstImg.errorImg),
    )
}


/**[AppDrawImg] is a Composable function in a Jetpack Compose application that displays an image.
 * @param imgId The resource ID of the image to be displayed. It should point to a valid image
 * resource in the application's resources.
 * @param contentDescription text used by accessibility services to describe what this image
 * represents.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content (ex.
 * background)
 * bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used.
 * */

@Composable
fun AppDrawImg(
    imgId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    Image(
        painter = painterResource(id = imgId),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}