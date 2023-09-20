package fit.asta.health.player.jetpack_audio.presentation.screens.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fit.asta.health.player.jetpack_audio.domain.utils.asFormattedString
import fit.asta.health.player.jetpack_audio.exo_player.common.MusicState
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerButtons
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerHeader
import fit.asta.health.player.jetpack_audio.presentation.screens.player.components.PlayerImage
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.MinContrastOfPrimaryVsSurface
import fit.asta.health.player.jetpack_audio.presentation.utils.DynamicThemePrimaryColorsFromImage
import fit.asta.health.player.jetpack_audio.presentation.utils.contrastAgainst
import fit.asta.health.player.jetpack_audio.presentation.utils.rememberDominantColorState
import fit.asta.health.player.jetpack_audio.presentation.utils.verticalGradientScrim
import fit.asta.health.player.jetpack_video.media.ControllerState
import fit.asta.health.player.jetpack_video.media.Media
import fit.asta.health.player.jetpack_video.media.MediaState
import fit.asta.health.player.jetpack_video.media.TimeBar
import fit.asta.health.player.jetpack_video.video.ControllerType
import fit.asta.health.player.jetpack_video.video.UiState
import fit.asta.health.player.jetpack_video.video.component.PlayerControlViewController
import fit.asta.health.player.jetpack_video.video.component.RememberPlayer
import fit.asta.health.player.jetpack_video.video.component.SimpleController
import fit.asta.health.player.jetpack_video.video.utils.findActivity
import kotlinx.coroutines.delay


@Composable
fun FullscreenView(
    state: MediaState,
    controllerState: ControllerState,
    uiState: UiState,
    musicState: MusicState,
    visibility: Boolean,
    onAudioEvent: (PlayerEvent) -> Unit,
    onVisibility: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = !isLandscape
        systemUiController.isNavigationBarVisible = !isLandscape
    }
    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    RememberPlayer(
        onPause = {
            onAudioEvent(PlayerEvent.Pause)
        },
        onStart = {
            onAudioEvent(PlayerEvent.Play)
        }
    )

//    val state = rememberMediaState(player = player.takeIf { uiState.setPlayer })
//    val controllerState = rememberControllerState(state)

    val mediaContent = remember(uiState, musicState, visibility) {
        movableContentOf { isLandscape: Boolean, modifier: Modifier ->
            MediaContentView(
                isLandscape = isLandscape, modifier = modifier,
                uiState = uiState,
                state = state,
                musicState = musicState,
                visibility = visibility,
                onVisibility = onVisibility,
                onAudioEvent = onAudioEvent,
                controllerState = controllerState
            )
        }
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        LaunchedEffect(musicState.currentSong.artworkUri) {
            dominantColorState.updateColorsFromImageUrl(musicState.currentSong.artworkUri.toString())
        }
        if (!isLandscape) {
            mediaContent(
                false,
                Modifier
                    .fillMaxSize()
                    .verticalGradientScrim(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
            )
        }
    }
    if (isLandscape) {
        mediaContent(
            true,
            Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
                .background(Color.Black)
        )
    }
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
private fun MediaContentView(
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
    uiState: UiState,
    state: MediaState,
    controllerState: ControllerState,
    musicState: MusicState,
    visibility: Boolean,
    onAudioEvent: (PlayerEvent) -> Unit,
    onVisibility: () -> Unit
) {
    val activity = LocalContext.current.findActivity()!!
    var scrubbing by remember { mutableStateOf(false) }
    val enterFullscreen = {
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }
    val exitFullscreen = {
        @SuppressLint("SourceLockedOrientationActivity")
        // Will reset to SCREEN_ORIENTATION_USER later
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
    }
    val spacing = LocalSpacing.current
    Column(modifier) {
        AnimatedVisibility(visible = !isLandscape) {
            Column {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )
                PlayerHeader(
                    audioVideo = visibility, onBackPress = {},
                    onAudioVideo = onVisibility, more = {}
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            }
        }
        AnimatedVisibility(visible = visibility) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Media(
                    state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
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
                    controllerAutoShow = isLandscape,
                    controller = when (uiState.controllerType) {
                        ControllerType.None -> null
                        ControllerType.Simple -> @Composable { state1 ->
                            SimpleController(
                                state1,
                                Modifier
                                    .fillMaxSize()
                                    .aspectRatio(16f / 9f)
                                    .padding(bottom = 16.dp)
                            )
                        }

                        ControllerType.PlayerControlView -> @Composable { state1 ->
                            PlayerControlViewController(
                                state1,
                                Modifier
                                    .fillMaxSize()
                                    .aspectRatio(16f / 9f)
                                    .padding(bottom = 16.dp)
                            )
                        }
                    }
                )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(30.dp)),
                    onClick = if (isLandscape) exitFullscreen else enterFullscreen
                ) {
                    Icon(
                        imageVector = if (isLandscape) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                        contentDescription = null, tint = Color.White
                    )
                }
            }
        }
        AnimatedVisibility(visible = !visibility) {
            PlayerImage(
                modifier = Modifier
                    .fillMaxWidth(),
                trackImageUrl = musicState.currentSong.artworkUri,
            )
        }
        AnimatedVisibility(visible = !isLandscape) {
            Column {
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                Text(
                    text = musicState.currentSong.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceSmall)
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                Text(
                    text = musicState.currentSong.album,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceSmall)
                )
                Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(1000)
                        controllerState.triggerPositionUpdate()
                    }
                }
                TimeBar(
                    controllerState.durationMs,
                    controllerState.positionMs,
                    controllerState.bufferedPositionMs,
                    modifier = Modifier
                        .systemGestureExclusion()
                        .fillMaxWidth()
                        .height(28.dp),
                    contentPadding = PaddingValues(12.dp),
                    scrubberCenterAsAnchor = true,
                    onScrubStart = { scrubbing = true },
                    onScrubStop = { positionMs ->
                        scrubbing = false
                        controllerState.seekTo(positionMs)
                    }
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceMedium)
                ) {
                    Text(text = controllerState.positionMs.asFormattedString())
                    Spacer(modifier = Modifier.weight(1f))
                    Text(musicState.duration.asFormattedString())
                }
                Spacer(modifier = Modifier.height(spacing.spaceMediumLarge))
                PlayerButtons(
                    modifier = Modifier.fillMaxWidth(),
                    playWhenReady = musicState.playWhenReady,
                    play = { onAudioEvent(PlayerEvent.Play) },
                    pause = { onAudioEvent(PlayerEvent.Pause) },
                    replay10 = { onAudioEvent(PlayerEvent.SkipBack) },
                    forward10 = { onAudioEvent(PlayerEvent.SkipForward) },
                    next = { onAudioEvent(PlayerEvent.SkipNext) },
                    previous = { onAudioEvent(PlayerEvent.SkipPrevious) }
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            }
        }
    }
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitFullscreen()
            }
        }
    }
    val onBackPressedDispatcher = activity.onBackPressedDispatcher
    DisposableEffect(onBackPressedDispatcher) {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose { onBackPressedCallback.remove() }
    }
    SideEffect {
        onBackPressedCallback.isEnabled = isLandscape
        if (isLandscape) {
            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        }
    }
}