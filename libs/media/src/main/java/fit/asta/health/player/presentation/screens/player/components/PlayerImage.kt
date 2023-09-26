package fit.asta.health.player.presentation.screens.player.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.designsystemx.AstaThemeX


@Composable
fun PlayerImage(
    trackImageUrl: Uri,
    imageBitmap: ByteArray?,
    modifier: Modifier = Modifier,
) {
    val spacing = AstaThemeX.appSpacing
    Box(
        modifier = modifier.padding(horizontal = spacing.extraLarge)
    ) {
        val url = "https://dj9n1wsbrvg44.cloudfront.net/tags/Breathing+Tag.png"
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageBitmap ?: url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}
