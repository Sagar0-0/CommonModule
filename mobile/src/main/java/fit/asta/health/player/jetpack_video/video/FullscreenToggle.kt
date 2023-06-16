package fit.asta.health.player.jetpack_video.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.*
import android.content.res.Configuration
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fit.asta.health.player.jetpack_video.data.model.VideoItem
import fit.asta.health.player.jetpack_video.media.Media
import fit.asta.health.player.jetpack_video.media.MediaState
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.ShowBuffering
import fit.asta.health.player.jetpack_video.media.SurfaceType
import fit.asta.health.player.jetpack_video.media.rememberMediaState
import fit.asta.health.player.jetpack_video.video.component.Option
import fit.asta.health.player.jetpack_video.video.component.PlayerControlViewController
import fit.asta.health.player.jetpack_video.video.component.RememberPlayer
import fit.asta.health.player.jetpack_video.video.component.SimpleController
import fit.asta.health.player.jetpack_video.video.component.VideoCard
import fit.asta.health.player.jetpack_video.video.utils.ControllerTypes
import fit.asta.health.player.jetpack_video.video.utils.ResizeModes
import fit.asta.health.player.jetpack_video.video.utils.findActivity




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullscreenToggle(
    navController: NavHostController,
    player: Player,
    videos: SnapshotStateList<VideoItem>,
    uiState: UiState,
    event: (UiEvent) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = !isLandscape
        systemUiController.isNavigationBarVisible = !isLandscape
    }
    RememberPlayer(
        onPause = {
            event(UiEvent.Stop(it))
            Log.d("devil", "p:$videos ")
        },
        onStart = {
            event(UiEvent.Start(it))
            Log.d("devil", "s: ")
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
                videos = videos,
                setUrl = { event(UiEvent.SetUrl(it)) },
                SetSurfaceType = { event(UiEvent.SetSurfaceType(it)) },
                SetResizeMode = { event(UiEvent.SetResizeMode(it)) },
                keepContentOnPlayerReset = { event(UiEvent.KeepContentOnPlayerReset(it)) },
                useArtwork = { event(UiEvent.SetUseArtwork(it)) },
                SetShowBuffering = { event(UiEvent.SetShowBuffering(it)) },
                setPlayer = { event(UiEvent.SetPlayer(it)) },
                playWhenReady = { event(UiEvent.PlayWhenReady(it)) },
                controllerType = { event(UiEvent.SetControllerType(it)) },
                controllerAutoShow = { event(UiEvent.ControllerAutoShow(it)) },
                controllerHideOnTouchType = { event(UiEvent.ControllerHideOnTouchType(it)) },
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
                    IconButton(onClick = { navController.popBackStack() }) {
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
    videos: SnapshotStateList<VideoItem>,
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
    uiState: UiState,
    setUrl: (String) -> Unit,
    SetSurfaceType: (SurfaceType) -> Unit,
    SetResizeMode: (ResizeMode) -> Unit,
    keepContentOnPlayerReset: (Boolean) -> Unit,
    useArtwork: (Boolean) -> Unit,
    SetShowBuffering: (ShowBuffering) -> Unit,
    setPlayer: (Boolean) -> Unit,
    playWhenReady: (Boolean) -> Unit,
    controllerType: (ControllerType) -> Unit,
    controllerHideOnTouchType: (Boolean) -> Unit,
    controllerAutoShow: (Boolean) -> Unit,
    state: MediaState
) {
    val activity = LocalContext.current.findActivity()!!
    val enterFullscreen = { activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE }
    val exitFullscreen = {
        @SuppressLint("SourceLockedOrientationActivity")
        // Will reset to SCREEN_ORIENTATION_USER later
        activity.requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
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
               items(videos){item: VideoItem ->
                   VideoCard(isCurrentPlaying = uiState.url==item.mediaItem.mediaId, videoItem = item) {
                       setUrl(it.contentUri.toString())
                   }
               }
           }
//            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                Option("Url", Urls, uiState.url) { setUrl(it) }
//                Option("Surface Type", SurfaceTypes, uiState.surfaceType) { SetSurfaceType(it) }
//                Option("Resize Mode", ResizeModes, uiState.resizeMode) { SetResizeMode(it) }
//                BooleanOption(
//                    "Keep Content On Player Reset",
//                    uiState.keepContentOnPlayerReset
//                ) { keepContentOnPlayerReset(it) }
//                BooleanOption("Use Artwork", uiState.useArtwork) { useArtwork(it) }
//                Option(
//                    "Show Buffering",
//                    ShowBufferingValues,
//                    uiState.showBuffering
//                ) { SetShowBuffering(it) }
//                BooleanOption("Set Player", uiState.setPlayer) { setPlayer(it) }
//                Column(Modifier.padding(start = 18.dp)) {
//                    BooleanOption("Play When Ready", uiState.playWhenReady) { playWhenReady(it) }
//                }
//                Option("Controller", ControllerTypes, uiState.controllerType) { controllerType(it) }
//                Column(Modifier.padding(start = 18.dp)) {
//                    val enabled = uiState.controllerType != ControllerType.None
//                    BooleanOption(
//                        "Hide On Touch",
//                        uiState.controllerHideOnTouchType,
//                        enabled
//                    ) { controllerHideOnTouchType(it) }
//
//                    BooleanOption(
//                        "Auto Show",
//                        uiState.controllerAutoShow,
//                        enabled
//                    ) { controllerAutoShow(it) }
//                }
//            }
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
            if (activity.requestedOrientation == SCREEN_ORIENTATION_USER) {
                activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        } else {
            activity.requestedOrientation = SCREEN_ORIENTATION_USER
        }
    }
}
