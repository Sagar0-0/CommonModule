package fit.asta.health.feature.testimonials.list.view

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrlTools
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.testimonials.components.ArtistCard
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.PlayerControlViewController
import fit.asta.health.player.presentation.component.SimpleController
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer

@Composable
fun TstViewVideoLayout(
    tstVideoMedia: Testimonial
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level3)
    ) {
        PlayVideoLayout(tstVideoMedia.media)
        Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        ArtistCard(tstVideoMedia)
    }
}

@Composable
@androidx.annotation.OptIn(UnstableApi::class)
fun PlayVideoLayout(
    tstVideoMedia: List<Media>
) {

    Row(
        Modifier.fillMaxWidth()
    ) {
        if (tstVideoMedia.isNotEmpty()) {
            tstVideoMedia.first().let {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    VideoView(
                        videoUri = getVideoUrlTools(url = it.url)
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
    uiState: VideoState = VideoState(
        controllerType = ControllerType.Simple,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
) {
    val player by rememberManagedExoPlayer()
    val mediaItem = remember {
        MediaItem.Builder().setUri(videoUri.toUri()).setMediaId(videoUri).setMediaMetadata(
            MediaMetadata.Builder()
                .setArtworkUri(getImgUrl("/tags/Breathing+Tag.png").toUri()).build()
        ).build()
    }
    val state = rememberMediaState(player = player)
    LaunchedEffect(mediaItem, player) {
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            prepare()
            stop()
        }
    }

    Box(
        modifier = Modifier.background(Color.Black), contentAlignment = Alignment.Center
    ) {
        Media(
            state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AppTheme.aspectRatio.wideScreen)
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
                    SimpleController(
                        state1,
                        Modifier.fillMaxSize()
                    )
                }

                ControllerType.PlayerControlView -> @Composable { state1 ->
                    PlayerControlViewController(
                        state1,
                        Modifier.fillMaxSize()
                    )
                }
            })
    }
}