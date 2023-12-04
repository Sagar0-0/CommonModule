package fit.asta.health.subscription.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer

@Composable
fun VideoViewUI(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.None,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
    onPlay: Boolean,
    onPause: Boolean
) {
    val player by rememberManagedExoPlayer()

    val state = rememberMediaState(player = player)
    LaunchedEffect(player) {
        Log.d("TAG", "VideoView: ")
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }
    LaunchedEffect(onPlay) {
        if (onPlay) {
            player?.play()
        }
    }
    LaunchedEffect(onPause) {
        if (onPause) {
            player?.pause()
        }
    }

    Media(
        modifier = modifier,
        state = state,
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
        controller = null
    )
}