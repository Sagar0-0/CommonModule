package fit.asta.health.common.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing

@Composable
fun AppToolCardImage(
    model: Any?, contentDescription: String?,
) {

    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = Modifier
            .aspectRatio(aspectRatio.original)
            .clip(
                RoundedCornerShape(
                    bottomStart = spacing.small, bottomEnd = spacing.small
                )
            ),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.error_404),
        error = painterResource(id = R.drawable.error_404),
    )

}


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