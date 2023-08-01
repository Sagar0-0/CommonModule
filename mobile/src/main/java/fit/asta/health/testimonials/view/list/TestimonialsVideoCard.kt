package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getMediaUrl
import fit.asta.health.navigation.home.view.component.ArtistCard
import fit.asta.health.player.jetpack_video.media.Media
import fit.asta.health.player.jetpack_video.media.MediaState
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.rememberMediaState
import fit.asta.health.player.jetpack_video.video.ControllerType
import fit.asta.health.player.jetpack_video.video.component.PlayerControlViewController
import fit.asta.health.player.jetpack_video.video.component.RememberPlayer
import fit.asta.health.player.jetpack_video.video.component.SimpleController
import fit.asta.health.testimonials.model.domain.Media
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.tools.exercise.view.video.VideoState

@Composable
fun TstViewVideoLayout(
    tstVideoMedia: Testimonial,
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
fun PlayVideoLayout(tstVideoMedia: List<Media>, player: Player) {
    val state = rememberMediaState(player = player)
    Row(
        Modifier.fillMaxWidth()
    ) {
        if (tstVideoMedia.isNotEmpty()) {
            tstVideoMedia.forEach {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    VideoView(
                        videoUri = getMediaUrl(url = it.url), state = state, player = player
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
                .aspectRatio(aspectRatio.medium)
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