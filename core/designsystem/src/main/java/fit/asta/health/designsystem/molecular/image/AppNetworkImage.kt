package fit.asta.health.designsystem.molecular.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.designsystem.AppTheme


/**
 * The [AppNetworkImage] is a custom Composable function in Jetpack Compose, used to display
 * images asynchronously.
 *
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param alpha Optional opacity to be applied to the AsyncImagePainter when it is
 * rendered onscreen.
 * @param contentDescription Text used by accessibility services to describe what this image
 *  represents.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be
 *  used.
 * @param placeholder A [Painter] that is displayed while the image is loading.
 */
@Composable
fun AppNetworkImage(
    modifier: Modifier = Modifier,
    model: Any? = null,
    alpha: Float = AppTheme.alphaValues.level5,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = painterResource(id = AppConstImages.placeHolderImg)
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeholder,
        alpha = alpha,
        error = painterResource(id = AppConstImages.errorImg),
    )
}