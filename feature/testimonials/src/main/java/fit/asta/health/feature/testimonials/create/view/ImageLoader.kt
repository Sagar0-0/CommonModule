package fit.asta.health.feature.testimonials.create.view

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.feature.testimonials.components.SelectedImageView
import fit.asta.health.feature.testimonials.components.UploadTstMediaView
import fit.asta.health.feature.testimonials.create.vm.MediaType
import fit.asta.health.feature.testimonials.create.vm.MediaType.AfterImage
import fit.asta.health.feature.testimonials.create.vm.MediaType.BeforeImage
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnMediaClear
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.feature.testimonials.utils.getOneUrl
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
            getViewModel.onEvent(TestimonialEvent.OnMediaSelect(BeforeImage, uri))
        }
    val afterLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            getViewModel.onEvent(TestimonialEvent.OnMediaSelect(AfterImage, uri))
        }


    Column(modifier = modifier) {
        BodyTexts.Level2(text = "Upload Images")
        Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageSelectionView(
                modifier = Modifier
                    .weight(1f)
                    .dashedBorder(
                        width = 1.dp, radius = AppTheme.spacing.level2, color = Color(0xff8694A9)
                    ),
                img = imgBefore,
                launcher = beforeLauncher,
                getViewModel = getViewModel,
                mediaType = BeforeImage
            )
            ImageSelectionView(
                modifier = Modifier
                    .weight(1f)
                    .dashedBorder(
                        width = 1.dp, radius = AppTheme.spacing.level2, color = Color(0xff8694A9)
                    ),
                img = imgAfter,
                launcher = afterLauncher,
                getViewModel = getViewModel,
                mediaType = AfterImage
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ImageSelectionView(
    modifier: Modifier = Modifier,
    img: fit.asta.health.data.testimonials.model.Media,
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
                onImageClear = { getViewModel.onEvent(OnMediaClear(mediaType)) })
        }
    }
}