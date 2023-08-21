package fit.asta.health.designsystem.components

import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import fit.asta.health.designsystem.R

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    url: String,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .placeholder(AppCompatResources.getDrawable(context, R.drawable.placeholder_tag))
                .data(data = url)
                .apply(block = {
                    size(Size.ORIGINAL)
                }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier.fillMaxWidth(),
    )
}