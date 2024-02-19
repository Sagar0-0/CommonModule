package fit.asta.health.sunlight.feature.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun SelectableImageBox(
    modifier: Modifier = Modifier,
    imageLink: String? = null,
    @DrawableRes onError: Int = DrawR.drawable.ic_error_not_found,
    caption: String,
    isSelected: Boolean = false,
    selectedColor: Color = AppTheme.colors.surfaceTint,
    unSelectedColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    AppCard(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(AppTheme.spacing.level1),
        border = BorderStroke(1.dp, if (isSelected) selectedColor else unSelectedColor)
    ) {
        Box(
            modifier = modifier
                .size(160.dp)
//            .padding(16.dp)/*medium*/,
        ) {
            RemoteImage(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(4.dp)),
                imageLink = imageLink ?: "",
                contentScale = ContentScale.Fit,
                error = onError
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Done, contentDescription = null,
                    tint = selectedColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(AppTheme.spacing.level1)//small
                )
            }
            HeadingTexts.Level3(
                text = caption,
                color = AppTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)//small
            )

        }
    }
}


@Composable
fun SelectableImagePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = DrawR.drawable.background_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), // medium
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SelectableImageBox(onError = DrawR.drawable.background_app, caption = "48%") {

        }
        SelectableImageBox(onError = DrawR.drawable.background_app, caption = "48%") {

        }
    }
}

