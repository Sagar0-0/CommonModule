package fit.asta.health.tools.exercise.view.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fit.asta.health.player.jetpack_video.media.Media
import fit.asta.health.player.jetpack_video.media.MediaState
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.rememberMediaState
import fit.asta.health.player.jetpack_video.video.ControllerType
import fit.asta.health.player.jetpack_video.video.component.Option
import fit.asta.health.player.jetpack_video.video.component.PlayerControlViewController
import fit.asta.health.player.jetpack_video.video.component.RememberPlayer
import fit.asta.health.player.jetpack_video.video.component.SimpleController
import fit.asta.health.player.jetpack_video.video.utils.ControllerTypes
import fit.asta.health.player.jetpack_video.video.utils.ResizeModes
import fit.asta.health.player.jetpack_video.video.utils.findActivity
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(
    player: Player,
    uiState: VideoState,
    progress:Float,
    event: (VideoEvent) -> Unit,
    onBack :()->Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    LaunchedEffect(key1 =progress){
        delay(60000)
       event(VideoEvent.UpdateProgress(value = progress+1f))
    }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = !isLandscape
        systemUiController.isNavigationBarVisible = !isLandscape
    }
    RememberPlayer(
        onPause = {
            event(VideoEvent.Stop(it))
        },
        onStart = {
            event(VideoEvent.Start(it))
        }
    )

    val state = rememberMediaState(player = player.takeIf { uiState.setPlayer })


    val mediaContent = remember(uiState) {
        // TODO movableContentOf here doesn't avoid Media from recreating its surface view when
        // screen rotation changed. Seems like a bug of Compose.
        // see: https://kotlinlang.slack.com/archives/CJLTWPH7S/p1654734644676989
        movableContentOf { isLandscape: Boolean, modifier: Modifier ->
            MediaContent(
                isLandscape = isLandscape, modifier = modifier,
                SetResizeMode = { event(VideoEvent.SetResizeMode(it)) },
                controllerType = { event(VideoEvent.SetControllerType(it)) },
                uiState = uiState,
                state = state
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fullscreen Toggle") },
                navigationIcon = {
                    IconButton(onClick = {onBack()}) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) { padding ->
        if (!isLandscape) {
            mediaContent(
                false,
                Modifier
                    .padding(padding)
                    .fillMaxSize()
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

@Composable
private fun MediaContent(
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
    uiState: VideoState,
    SetResizeMode: (ResizeMode) -> Unit,
    controllerType: (ControllerType) -> Unit,
    state: MediaState
) {
    val activity = LocalContext.current.findActivity()!!
    val enterFullscreen = { activity.requestedOrientation =
        ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }
    val exitFullscreen = {
        @SuppressLint("SourceLockedOrientationActivity")
        // Will reset to SCREEN_ORIENTATION_USER later
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
    }
    Column(modifier) {
        Box(
            modifier = Modifier
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
                controllerAutoShow = uiState.controllerAutoShow,
                controller = when (uiState.controllerType) {
                    ControllerType.None -> null
                    ControllerType.Simple -> @Composable { state1 ->
                        SimpleController(state1, Modifier.fillMaxSize())
                    }

                    ControllerType.PlayerControlView -> @Composable { state1 ->
                        PlayerControlViewController(state1, Modifier.fillMaxSize())
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
                Icon(Icons.Filled.Fullscreen, contentDescription = null, tint = Color.White)
            }
        }

        AnimatedVisibility(visible = !isLandscape) {
            LazyColumn(modifier = Modifier.fillMaxWidth()){
                item {
                    Option("Resize Mode", ResizeModes, uiState.resizeMode) { SetResizeMode(it) }
                }
                item {
                    Option("Controller", ControllerTypes, uiState.controllerType) { controllerType(it) }
                }
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
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        }
    }
}
