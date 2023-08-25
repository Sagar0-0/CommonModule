package fit.asta.health.testimonials.ui.create.view

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.jetpack.getOneUrl
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.jetpack.dashedBorder
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.testimonials.data.model.Media
import fit.asta.health.testimonials.ui.components.SelectedImageView
import fit.asta.health.testimonials.ui.components.UploadTstMediaView
import fit.asta.health.testimonials.ui.create.vm.MediaType
import fit.asta.health.testimonials.ui.create.vm.TestimonialEvent
import fit.asta.health.testimonials.ui.create.vm.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ImageLayout(
    modifier: Modifier = Modifier,
    getViewModel: TestimonialViewModel,
) {

    val imgBefore by getViewModel.imgBefore.collectAsStateWithLifecycle()
    val imgAfter by getViewModel.imgAfter.collectAsStateWithLifecycle()

    val beforeLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            getViewModel.onEvent(TestimonialEvent.OnMediaSelect(MediaType.BeforeImage, uri))
        }
    val afterLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            getViewModel.onEvent(TestimonialEvent.OnMediaSelect(MediaType.AfterImage, uri))
        }


    Column(modifier = modifier) {
        AppTexts.BodyMedium(text = "Upload Images")
        Spacer(modifier = Modifier.height(spacing.medium))
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.minSmall),
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageSelectionView(
                modifier = Modifier
                    .weight(1f)
                    .dashedBorder(width = 1.dp, radius = spacing.small, color = Color(0xff8694A9)),
                img = imgBefore,
                launcher = beforeLauncher,
                getViewModel = getViewModel,
                mediaType = MediaType.BeforeImage
            )
            ImageSelectionView(
                modifier = Modifier
                    .weight(1f)
                    .dashedBorder(width = 1.dp, radius = spacing.small, color = Color(0xff8694A9)),
                img = imgAfter,
                launcher = afterLauncher,
                getViewModel = getViewModel,
                mediaType = MediaType.AfterImage
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ImageSelectionView(
    modifier: Modifier = Modifier,
    img: Media,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    getViewModel: TestimonialViewModel,
    mediaType: MediaType,
) {
    Box(
        modifier = modifier
    ) {
        if (img.url.isEmpty() && img.localUrl == null) {
            UploadTstMediaView(title = img.title, onUploadClick = { launcher.launch("image/*") })
        } else {
            SelectedImageView(title = img.title,
                url = getOneUrl(img.localUrl, img.url),
                onImageClick = { launcher.launch("image/*") },
                onImageClear = { getViewModel.onEvent(TestimonialEvent.OnMediaClear(mediaType)) })
        }
    }
}