package fit.asta.health.feature.testimonials.create.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.components.UploadTstMediaView
import fit.asta.health.feature.testimonials.create.vm.MediaType.Video
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnMediaClear
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun TstGetVideo(viewModel: TestimonialViewModel = hiltViewModel()) {

    val videoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            viewModel.onEvent(TestimonialEvent.OnMediaSelect(Video, uri))
        }

    GetVideo(viewModel = viewModel, onVideoClick = {
        videoLauncher.launch("video/*")
    }, onVideoClear = { viewModel.onEvent(OnMediaClear(Video)) })

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
        TitleTexts.Level2(text = "Upload Video")
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        Box(
            modifier = Modifier
                .dashedBorder(
                    width = 1.dp, radius = AppTheme.spacing.level1, color = Color(0xff8694A9)
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
    //val state = rememberMediaState(player = viewModel.player())
    Box {
        if (video.url.isEmpty() && video.localUrl == null) {
            UploadTstMediaView(onUploadClick = onVideoClick)
        } else {
            /*VideoView(
                videoUri = getOneUrl(localUrl = video.localUrl, remoteUrl = video.url),
                player = viewModel.player(),
                state = state
            )*/
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