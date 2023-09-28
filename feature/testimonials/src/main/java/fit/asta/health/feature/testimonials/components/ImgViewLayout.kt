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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme

@Composable
fun UploadTstMediaView(
    title: String? = null,
    onUploadClick: () -> Unit = {},
) {
    Box(
        Modifier
            .padding(AppTheme.appSpacing.minSmall)
            .aspectRatio(AppTheme.appAspectRatio.square)
            .clickable { onUploadClick() }
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppDefaultIcon(
                imageVector = Icons.Filled.UploadFile,
                contentDescription = "Upload Image",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(AppTheme.appSpacing.small))
            AppTexts.BodyMedium(
                text = "Browse to Choose", color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
            title?.let {
                AppTexts.HeadlineSmall(text = it, color = MaterialTheme.colorScheme.onSurface)
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
            .padding(AppTheme.appSpacing.minSmall)
            .aspectRatio(AppTheme.appAspectRatio.square)
            .clickable { onImageClick() }, contentAlignment = Alignment.BottomCenter
    ) {
        AppDefServerImg(
            model = url,
            contentDescription = "Selected Tst Image",
            modifier = Modifier.aspectRatio(AppTheme.appAspectRatio.square),
            contentScale = ContentScale.Crop
        )
        AppTexts.TitleMedium(text = title, color = MaterialTheme.colorScheme.onSurface)
    }
    ClearTstMedia(onImageClear)
}