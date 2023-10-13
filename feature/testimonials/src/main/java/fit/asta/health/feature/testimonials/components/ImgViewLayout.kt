package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun UploadTstMediaView(
    title: String? = null,
    onUploadClick: () -> Unit = {},
) {
    Box(
        Modifier
            .padding(AppTheme.spacing.level0)
            .aspectRatio(AppTheme.aspectRatio.square)
            .clickable { onUploadClick() }
            .clip(AppTheme.shape.level1)
            .background(color = AppTheme.colors.tertiaryContainer),
        contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppIcon(
                imageVector = Icons.Filled.UploadFile,
                contentDescription = "Upload Image",
                tint = AppTheme.colors.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            BodyTexts.Level2(text = "Browse to Choose", color = AppTheme.colors.onTertiaryContainer)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            title?.let {
                HeadingTexts.Level3(text = it)
            }
        }
    }
}


@Composable
fun SelectedImageView(
    title: String,
    url: String,
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
) {

    Box(
        Modifier
            .padding(AppTheme.spacing.level0)
            .aspectRatio(AppTheme.aspectRatio.square)
            .clickable { onImageClick() }, contentAlignment = Alignment.BottomCenter
    ) {
        AppNetworkImage(
            model = url,
            contentDescription = "Selected Tst Image",
            modifier = Modifier.aspectRatio(AppTheme.aspectRatio.square),
            contentScale = ContentScale.Crop
        )
        TitleTexts.Level2(text = title)
    }
    ClearTstMedia(onImageClear)
}