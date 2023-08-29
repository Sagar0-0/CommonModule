package fit.asta.health.feature.testimonials.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.Player
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.feature.testimonials.components.ArtistCard

@Composable
fun TstViewVideoLayout(
    tstVideoMedia: fit.asta.health.data.testimonials.model.Testimonial,
    player: Player,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        PlayVideoLayout(tstVideoMedia.media, player)
        Spacer(modifier = Modifier.height(spacing.medium))
        ArtistCard(tstVideoMedia)
    }
}

@Composable
fun PlayVideoLayout(
    tstVideoMedia: List<Media>,
    player: Player
) {
    val state = rememberMediaState(player = player)
    Row(
        Modifier.fillMaxWidth()
    ) {
        if (tstVideoMedia.isNotEmpty()) {
            tstVideoMedia.forEach {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    VideoView(
                        videoUri = getImgUrl(url = it.url), state = state, player = player
                    )
                }
            }
        } else {
            Text(text = "MEDIA FILE NOT FOUND")
        }
    }
}

@Composable
fun VideoView(
    videoUri: String,
    player: Player,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.Simple,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
    state: MediaState,
) {

    player.apply {
        setMediaItem(
            androidx.media3.common.MediaItem.Builder().setUri(videoUri.toUri()).setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtworkUri("https://img2.asta.fit/tags/Breathing+Tag.png".toUri()).build()
            ).build()
        )
        playWhenReady = false
        prepare()
        stop()
    }
    RememberPlayer(onPause = {
        player.stop()
    }, onStart = {
        player.play()
    })

    Box(
        modifier = Modifier.background(Color.Black), contentAlignment = Alignment.Center
    ) {
        Media(state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio.wideScreen)
                .background(Color.Black),
            surfaceType = uiState.surfaceType,
            resizeMode = uiState.resizeMode,
            keepContentOnPlayerReset = uiState.keepContentOnPlayerReset,
            useArtwork = uiState.useArtwork,
            showBuffering = uiState.showBuffering,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            errorMessage = { error ->
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        error.message ?: "",
                        modifier = Modifier
                            .background(Color(0x80808080), RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            },
            controllerHideOnTouch = uiState.controllerHideOnTouchType,
            controllerAutoShow = uiState.controllerAutoShow,
            controller = when (uiState.controllerType) {
                ControllerType.None -> null
                ControllerType.Simple -> @Composable { state1 ->
                    SimpleController(state1, Modifier.fillMaxSize())
                }

                ControllerType.PlayerControlView -> @Composable { state1 ->
                    PlayerControlViewController(state1, Modifier.fillMaxSize())
                }
            })
    }
}