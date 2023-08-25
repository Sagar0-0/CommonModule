package fit.asta.health.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.AsyncImage
import fit.asta.health.designsystem.atomic.LocalTintTheme

/**
 * A wrapper around [AsyncImage] which determines the colorFilter based on the theme
 */
@Composable
fun AstaAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
) {
    val iconTint = LocalTintTheme.current.iconTint
    AsyncImage(
        placeholder = placeholder,
        model = imageUrl,
        contentDescription = contentDescription,
        colorFilter = iconTint?.let { ColorFilter.tint(it) },
        modifier = modifier,
    )
}