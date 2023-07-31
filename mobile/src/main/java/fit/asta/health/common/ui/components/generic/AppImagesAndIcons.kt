package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.Image
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

object AppConstImg {
    val placeHolderImg = R.drawable.error_404
    val errorImg = R.drawable.error_404
}

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
        placeholder = painterResource(id = AppConstImg.placeHolderImg),
        error = painterResource(id = AppConstImg.errorImg),
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