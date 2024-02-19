package fit.asta.health.sunlight.feature.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun RemoteImage(
    modifier: Modifier,
    imageLink: String,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes error: Int = DrawR.drawable.error_404,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = imageLink,
        contentDescription = null,
        contentScale = contentScale,
        error = if (error != -1) painterResource(id = error) else null,
        placeholder = painterResource(id = DrawR.drawable.weather_sun),
        onError = onError
    )
}