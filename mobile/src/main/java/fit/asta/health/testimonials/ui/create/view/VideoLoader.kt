package fit.asta.health.testimonials.ui.create.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import fit.asta.health.common.jetpack.dashedBorder
import fit.asta.health.common.jetpack.getOneUrl
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.player.jetpack_video.media.rememberMediaState
import fit.asta.health.testimonials.ui.components.UploadTstMediaView
import fit.asta.health.testimonials.ui.create.vm.MediaType
import fit.asta.health.testimonials.ui.create.vm.TestimonialEvent
import fit.asta.health.testimonials.ui.create.vm.TestimonialViewModel
import fit.asta.health.testimonials.ui.list.view.VideoView
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun TstGetVideo(viewModel: TestimonialViewModel = hiltViewModel()) {

    val videoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            viewModel.onEvent(TestimonialEvent.OnMediaSelect(MediaType.Video, uri))
        }

    GetVideo(viewModel = viewModel, onVideoClick = {
        videoLauncher.launch("video/*")
    }, onVideoClear = { viewModel.onEvent(TestimonialEvent.OnMediaClear(MediaType.Video)) })

}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GetVideo(
    modifier: Modifier = Modifier,
    viewModel: TestimonialViewModel,
    onVideoClick: () -> Unit = {},
    onVideoClear: () -> Unit,
) {
    Column(modifier = modifier) {
        AppTexts.TitleMedium(text = "Upload Video", color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(spacing.medium))
        Box(
            modifier = Modifier
                .dashedBorder(
                    width = 1.dp, radius = spacing.small, color = Color(0xff8694A9)
                )
                .fillMaxWidth()
        ) {
            VideoLayout(
                viewModel = viewModel, onVideoClear = onVideoClear, onVideoClick = onVideoClick
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun VideoLayout(
    viewModel: TestimonialViewModel,
    onVideoClick: () -> Unit = {},
    onVideoClear: () -> Unit,
) {
    val video by viewModel.video.collectAsStateWithLifecycle()
    val state = rememberMediaState(player = viewModel.player())
    Box {
        if (video.url.isEmpty() && video.localUrl == null) {
            UploadTstMediaView(onUploadClick = onVideoClick)
        } else {
            VideoView(
                videoUri = getOneUrl(localUrl = video.localUrl, remoteUrl = video.url),
                player = viewModel.player(),
                state = state
            )
        }
    }

}

@Composable
private fun PlayOrDestroy(
    player: ExoPlayer,
    playWhenReady: Boolean,
) {
    LaunchedEffect(player) {
        player.playWhenReady = playWhenReady
        player.prepare()
    }
}