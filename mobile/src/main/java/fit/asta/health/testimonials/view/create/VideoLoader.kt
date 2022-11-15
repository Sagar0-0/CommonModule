package fit.asta.health.testimonials.view.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import fit.asta.health.R

@Composable
fun GetVideo(
    modifier: Modifier = Modifier,
    onSelectedVideo: Uri? = null,
    onVideoClear: () -> Unit,
    onVideoClick: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Text(text = "Upload Video",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .dashedBorder(width = 1.dp, radius = 8.dp, color = Color(0xff8694A9))
            .fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(0.dp)) {

                VideoLayout(onVideoClear = onVideoClear,
                    onSelectedVideo = onSelectedVideo,
                    onVideoClick = onVideoClick)

            }
        }
    }
}


@Composable
fun VideoLayout(
    onSelectedVideo: Uri? = null,
    onVideoClear: () -> Unit,
    onVideoClick: (() -> Unit)? = null,
) {

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current
    val player = ExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = onSelectedVideo?.let { MediaItem.fromUri(it) }
    val playWhenReady by rememberSaveable { mutableStateOf(true) }
    mediaItem?.let { player.setMediaItem(it) }
    playerView.player = player

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    var isVideoDeleted by remember { mutableStateOf(false) }

    Box {

        if (onSelectedVideo != null && !isVideoDeleted) {
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
                    isVideoDeleted = true
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_delete_forever),
                        contentDescription = null,
                        tint = Color(0xffFF4081))
                }
            }
        } else {
            UploadVideo(onVideoClick)
            isVideoDeleted = false
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
    Box(Modifier.padding(2.dp), contentAlignment = Alignment.Center) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onVideoClick?.let { it() } }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Browse to choose a File")
            }
        }
    }
}


@Preview
@Composable
fun TestGetVideo() {

    var selectedVideo by remember { mutableStateOf<Uri?>(null) }
    val videoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedVideo = uri
        }

    GetVideo(onVideoClear = { selectedVideo = null },
        onVideoClick = { videoLauncher.launch("video/*") },
        onSelectedVideo = selectedVideo)

}