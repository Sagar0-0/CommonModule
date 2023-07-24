package fit.asta.health.testimonials.view.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.common.ui.theme.imageHeight
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.testimonials.view.components.ClearTstMedia
import fit.asta.health.testimonials.view.components.UploadTstMediaView
import fit.asta.health.testimonials.viewmodel.create.MediaType
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ImageLayout(
    modifier: Modifier = Modifier,
    getViewModel: TestimonialViewModel,
    //onNavigateBeforeImgCropper: () -> Unit = {},
    //onNavigateAfterImgCropper: () -> Unit = {},
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

        Text(text = "Upload Images", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier
                .dashedBorder(width = 1.dp, radius = 8.dp, color = Color(0xff8694A9))
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    if (imgBefore.url.isEmpty() && imgBefore.localUrl == null) {

                        UploadTstMediaView(title = imgBefore.title,
                            onUploadClick = { beforeLauncher.launch("image/*") })

//                        UploadTstMediaView(title = imgBefore.title,
//                            onUploadClick = { onNavigateBeforeImgCropper() })

                    } else {

                        SelectedImageView(title = imgBefore.title,
                            url = getOneUrl(imgBefore.localUrl, imgBefore.url),
                            onImageClick = { beforeLauncher.launch("image/*") },
                            onImageClear = {
                                getViewModel.onEvent(
                                    TestimonialEvent.OnMediaClear(
                                        MediaType.BeforeImage
                                    )
                                )
                            })

//                        SelectedImageView(title = imgBefore.title,
//                            url = getOneUrl(imgBefore.localUrl, imgBefore.url),
//                            onImageClick = { onNavigateBeforeImgCropper() },
//                            onImageClear = {
//                                getViewModel.onEvent(
//                                    TestimonialEvent.OnMediaClear(
//                                        MediaType.BeforeImage
//                                    )
//                                )
//                            })
                    }
                }

                Box(modifier = Modifier.fillMaxWidth(1f)) {
                    if (imgAfter.url.isEmpty() && imgAfter.localUrl == null) {

                        UploadTstMediaView(title = imgAfter.title,
                            onUploadClick = { afterLauncher.launch("image/*") })

//                        UploadTstMediaView(
//                            title = imgAfter.title,
//                            onUploadClick = { onNavigateAfterImgCropper() })

                    } else {

                        SelectedImageView(title = imgAfter.title,
                            url = getOneUrl(imgAfter.localUrl, imgAfter.url),
                            onImageClick = { afterLauncher.launch("image/*") },
                            onImageClear = {
                                getViewModel.onEvent(
                                    TestimonialEvent.OnMediaClear(
                                        MediaType.AfterImage
                                    )
                                )
                            })

//                        SelectedImageView(title = imgAfter.title,
//                            url = getOneUrl(imgAfter.localUrl, imgAfter.url),
//                            onImageClick = { onNavigateAfterImgCropper() },
//                            onImageClear = {
//                                getViewModel.onEvent(
//                                    TestimonialEvent.OnMediaClear(
//                                        MediaType.AfterImage
//                                    )
//                                )
//                            })

                    }
                }
            }
        }
    }
}

@Composable
fun getOneUrl(localUrl: Uri?, remoteUrl: String): String {
    return localUrl?.toString() ?: getImageUrl(remoteUrl)
}

@Composable
private fun SelectedImageView(
    title: String,
    url: String,
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
) {

    Box(
        Modifier
            .padding(spacing.minSmall)
            .clickable { onImageClick() },
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = url),
            contentDescription = null,
            Modifier
                .fillMaxWidth(1f)
                .height(imageHeight.large)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(spacing.small)
        )
    }

    ClearTstMedia(onImageClear)
}
