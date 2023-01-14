package fit.asta.health.testimonials.view.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import fit.asta.health.R
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import fit.asta.health.ui.spacing
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun TestGetVideo(viewModel: TestimonialViewModel = hiltViewModel()) {

    val videoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            viewModel.onEvent(TestimonialEvent.OnVideoSelect(uri))
        }

    GetVideo(viewModel = viewModel, onVideoClick = {
        videoLauncher.launch("video/*")
        viewModel.onEvent(TestimonialEvent.OnMediaIndex(0))
    }, onVideoClear = { viewModel.onEvent(TestimonialEvent.OnVideoClear(0)) })
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GetVideo(
    modifier: Modifier = Modifier,
    viewModel: TestimonialViewModel,
    onVideoClick: (() -> Unit)? = null,
    onVideoClear: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = "Upload Video", color = Color.Black, style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier
                .dashedBorder(width = 1.dp, radius = 8.dp, color = Color(0xff8694A9))
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                VideoLayout(
                    viewModel = viewModel, onVideoClear = onVideoClear, onVideoClick = onVideoClick
                )
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun VideoLayout(
    viewModel: TestimonialViewModel,
    onVideoClick: (() -> Unit)? = null,
    onVideoClear: () -> Unit,
) {

    val playWhenReady by rememberSaveable { mutableStateOf(true) }
    val player = ExoPlayer.Builder(LocalContext.current).build()
    val playerView = PlayerView(LocalContext.current)
    playerView.player = player

    val lifecycleOwner = LocalLifecycleOwner.current
    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box {

        val media = viewModel.data.vdoMedia[0]
        if (media.url.isEmpty() && media.localUrl == null) {

            UploadVideo(onVideoClick)
        } else {

            val mediaItem = getOneUrl(media.localUrl, media.url).let { MediaItem.fromUri(it) }
            mediaItem.let { player.setMediaItem(it) }

            Box(Modifier.padding(2.dp), contentAlignment = Alignment.BottomCenter) {

                AndroidView(factory = {
                    playerView
                }, modifier = Modifier.aspectRatio(16f / 09f), update = {
                    when (lifecycle) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                            it.player?.pause()
                        }
                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()
                        }
                        else -> Unit
                    }
                })
            }

            Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    onVideoClear()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_forever),
                        contentDescription = null,
                        tint = Color(0xffFF4081)
                    )
                }
            }
        }
    }

    PlayOrDestroy(player, playWhenReady)
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

@Composable
private fun UploadVideo(onVideoClick: (() -> Unit)?) {
    Box(Modifier.padding(spacing.minSmall), contentAlignment = Alignment.Center) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onVideoClick?.let { it() } }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(spacing.small))

                Text(text = "Browse to choose a video")
            }
        }
    }
}