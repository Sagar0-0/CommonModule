package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.PlayerControlViewController
import fit.asta.health.player.presentation.component.SimpleController
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer


@Composable
fun TestimonialsVideoView(
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
            MediaMetadata.Builder().setArtworkUri(getImgUrl("/tags/Breathing+Tag.png").toUri())
                .build()
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
        modifier = Modifier.background(Color.Black),
        contentAlignment = Alignment.Center
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
                    AppCircularProgressIndicator()
                }
            },
            errorMessage = { error ->
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    BodyTexts.Level1(text = error.message ?: "", color = AppTheme.colors.error)
                }
            },
            controllerHideOnTouch = uiState.controllerHideOnTouchType,
            controllerAutoShow = uiState.controllerAutoShow,
            controller = when (uiState.controllerType) {
                ControllerType.None -> null
                ControllerType.Simple -> @Composable { state1 ->
                    SimpleController(
                        state1, Modifier.fillMaxSize()
                    )
                }

                ControllerType.PlayerControlView -> @Composable { state1 ->
                    PlayerControlViewController(
                        state1, Modifier.fillMaxSize()
                    )
                }
            })
    }
}