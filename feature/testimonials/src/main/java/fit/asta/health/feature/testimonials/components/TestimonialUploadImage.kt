package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.events.MediaType
import fit.asta.health.feature.testimonials.utils.getOneUrl


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            TestimonialUploadImage(
                media = listOf(),
                onImageSelected = { _, _ -> },
                onImageDelete = {}
            )
        }
    }
}

/**
 * This function creates the UI to show the Images Upload Options and also helps to fetch the images
 * from the device and show them in the UI accordingly.
 *
 * @param media This variable contains the images that are chosen by the user
 * @param onImageSelected This function is invoked when the user choose a certain image from the
 * device
 * @param onImageDelete This function is invoked when the user decide to remove a certain image.
 */
@Composable
fun TestimonialUploadImage(
    media: List<Media>,
    onImageSelected: (MediaType, Uri?) -> Unit,
    onImageDelete: (MediaType) -> Unit
) {

    // Current Media type is used like an flag/ shared variable to keep track of what to select/delete
    var currentMediaType: MediaType = MediaType.BeforeImage

    // Image Launcher
    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            onImageSelected(currentMediaType, uri)
        }

    // Parent Composable
    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {

        // Upload Image Text
        BodyTexts.Level2(text = "Upload Images")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {

            for (i in 0..1) {

                // Local Current Media Type
                val currType = if (i == 0) MediaType.BeforeImage else MediaType.AfterImage

                // Image Slot UI
                ImageSlotsUI(
                    media = media[i],
                    onSelectImageClick = {
                        currentMediaType = currType
                        imageLauncher.launch("image/*")
                    },
                    onDeleteImageClick = { onImageDelete(currType) }
                )
            }
        }
    }
}


/**
 * This function shows the UI for the Upload Image Slots and are kind of a child function to
 * [TestimonialUploadImage] function.
 *
 * @param media This is the Media file chosen by the user
 * @param onSelectImageClick This is the function that is invoked when the user choose a file
 * @param onDeleteImageClick This is the function that is invoked when the user deletes a file
 */
@Composable
private fun RowScope.ImageSlotsUI(
    media: Media,
    onSelectImageClick: () -> Unit,
    onDeleteImageClick: () -> Unit
) {

    // Parent Composable
    Box(
        modifier = Modifier
            .weight(1f)
            .background(AppTheme.colors.tertiaryContainer)
            .dashedBorder(
                width = 1.dp,
                radius = AppTheme.spacing.level1,
                color = AppTheme.colors.onSurface
            )
            .clickable { onSelectImageClick() }
            .aspectRatio(AppTheme.aspectRatio.square)
            .clip(AppTheme.shape.level1),
        contentAlignment = Alignment.Center
    ) {

        // Checking if the media objects already have some images or not
        if (media.url.isEmpty() && media.localUrl == null) {

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Upload Icon
                AppIcon(
                    imageVector = Icons.Filled.UploadFile,
                    contentDescription = "Upload Image",
                    tint = AppTheme.colors.onTertiaryContainer
                )

                // Choose file text
                BodyTexts.Level2(
                    text = "Browse to Choose",
                    color = AppTheme.colors.onTertiaryContainer
                )

                // Says what type this file is of
                HeadingTexts.Level3(text = media.title)
            }
        } else {

            // Image chosen by the user
            AppNetworkImage(
                model = getOneUrl(media.localUrl, media.url),
                contentDescription = "Selected Testimonial Image",
                modifier = Modifier.aspectRatio(AppTheme.aspectRatio.square),
                contentScale = ContentScale.Crop
            )

            // Title of the Image
            TitleTexts.Level2(text = media.title)

            // Delete Button
            AppIconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                imageVector = Icons.Filled.Delete,
                iconTint = AppTheme.colors.error,
                onClick = onDeleteImageClick
            )
        }
    }
}