package fit.asta.health.testimonials.view.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.testimonials.view.theme.boxSize
import fit.asta.health.testimonials.view.theme.cardHeight
import fit.asta.health.testimonials.view.theme.imageHeight
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import fit.asta.health.ui.spacing
import fit.asta.health.ui.theme.TextLight04
import fit.asta.health.utils.getImageUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestGetImage(viewModel: TestimonialViewModel = hiltViewModel()) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            viewModel.onEvent(TestimonialEvent.OnImageSelect(uri))
        }

    ImagePreviewLayout(viewModel = viewModel, onImageClick = {
        launcher.launch("image/*")
        viewModel.onEvent(TestimonialEvent.OnMediaIndex(it))
    }, onImageClear = { viewModel.onEvent(TestimonialEvent.OnImageClear(it)) })
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ImagePreviewLayout(
    modifier: Modifier = Modifier,
    viewModel: TestimonialViewModel,
    onImageClick: (inx: Int) -> Unit,
    onImageClear: (inx: Int) -> Unit,
) {

    Column(modifier = modifier) {

        Text(text = "Upload Images", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier
                .dashedBorder(width = 1.dp, radius = 8.dp, color = Color(0xff8694A9))
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier
                        .fillMaxWidth()
                        .height(boxSize.large)
//                        .heightIn(min = 200.dp)
                        .padding(horizontal = spacing.medium)
                ) {

                    viewModel.data.imgMedia.forEachIndexed { index, media ->
                        item {
                            Box {
                                if (media.url.isEmpty() && media.localUrl == null) {
                                    UploadImageView(
                                        title = media.title,
                                        inx = index,
                                        onImageClick = onImageClick
                                    )
                                } else {
                                    SelectedImageView(
                                        title = media.title,
                                        inx = index,
                                        url = getOneUrl(media.localUrl, media.url),
                                        onImageClick = onImageClick,
                                        onImageClear = onImageClear
                                    )
                                }
                            }
                        }
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
    inx: Int,
    url: String,
    onImageClick: (inx: Int) -> Unit,
    onImageClear: (inx: Int) -> Unit,
) {

    Box(
        Modifier
            .padding(spacing.minSmall)
            .clickable { onImageClick(inx) },
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = url),
            contentDescription = null,
            Modifier
                .fillMaxWidth(1f)
                .height(imageHeight.large)
//                .heightIn(min = 180.dp)
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

    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
        IconButton(onClick = {
            onImageClear(inx)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_forever),
                contentDescription = null,
                tint = Color(0xffFF4081)
            )
        }
    }
}

@Composable
private fun UploadImageView(title: String, inx: Int, onImageClick: (inx: Int) -> Unit) {

    Box(Modifier.padding(spacing.minSmall), contentAlignment = Alignment.Center) {

        Card(
            Modifier
                .fillMaxWidth(1f)
                .height(cardHeight.medium)
//                .heightIn(min = 180.dp)
                .clickable { onImageClick(inx) }) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.size(imageHeight.small)
                )

                Spacer(modifier = Modifier.height(spacing.small))

                Text(text = "Browse to choose")

                Text(
                    text = title,
                    color = TextLight04,
                    modifier = Modifier.padding(spacing.small),
                    style = MaterialTheme.typography.headlineMedium
                )

            }
        }
    }
}