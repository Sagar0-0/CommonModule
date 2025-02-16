package fit.asta.health.player.presentation.screens.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.Player
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.player.audio.common.MusicState
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.asFormattedString
import fit.asta.health.player.domain.utils.findActivity
import fit.asta.health.player.media.ControllerState
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.MediaState
import fit.asta.health.player.media.TimeBar
import fit.asta.health.player.media.rememberControllerState
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.UiState
import fit.asta.health.player.presentation.component.PlayerControlViewController
import fit.asta.health.player.presentation.component.RememberPlayer
import fit.asta.health.player.presentation.component.SimpleController
import fit.asta.health.player.presentation.screens.player.components.PlayerButtons
import fit.asta.health.player.presentation.screens.player.components.PlayerHeader
import fit.asta.health.player.presentation.screens.player.components.PlayerImage
import fit.asta.health.player.presentation.utils.DynamicThemePrimaryColorsFromImage
import fit.asta.health.player.presentation.utils.contrastAgainst
import fit.asta.health.player.presentation.utils.rememberDominantColorState
import fit.asta.health.player.presentation.utils.verticalGradientScrim
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.delay

const val MinContrastOfPrimaryVsSurface = 3f


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    player: Player,
    trackList: SnapshotStateList<String>,
    musicList: SnapshotStateList<Song>,
    selectedTrack: String,
    uiState: UiState,
    musicState: MusicState,
    visibility: Boolean,
    onAudioEvent: (PlayerEvent) -> Unit,
    onVisibility: () -> Unit,
    onTrackChange: (String) -> Unit,
    onBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = !isLandscape
        systemUiController.isNavigationBarVisible = !isLandscape
    }
    val surfaceColor = AppTheme.colors.surface
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

    val state = rememberMediaState(player = player.takeIf { uiState.setPlayer })
    val controllerState = rememberControllerState(state)
    val sheetState = rememberStandardBottomSheetState(
        initialValue = if (isLandscape) SheetValue.Hidden
        else SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val lazyListState = rememberLazyListState()
    val mediaContent = remember(uiState, selectedTrack, trackList, musicList) {
        movableContentOf { isLandscape: Boolean, visibility: Boolean, musicState: MusicState, modifier: Modifier ->
            MediaContentView(
                isLandscape = isLandscape, modifier = modifier,
                trackList = trackList,
                selectedTrack = selectedTrack,
                uiState = uiState,
                state = state,
                musicState = musicState,
                visibility = visibility,
                onVisibility = onVisibility,
                onAudioEvent = onAudioEvent,
                controllerState = controllerState,
                onTrackChange = onTrackChange,
                onBack = onBack
            )
        }
    }
    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        LaunchedEffect(
            musicState.currentSong.artworkUri,
            state.playerState?.mediaMetadata?.artworkData
        ) {
            dominantColorState.updateColorsFromImageUrl(
                musicState.currentSong.artworkUri.toString(),
                state.playerState?.mediaMetadata?.artworkData
            )
        }
        AppBottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            sheetContainerColor = AppTheme.colors.primary,
            sheetContentColor = AppTheme.colors.onPrimary,
            sheetDragHandle = { BottomSheetDefaults.DragHandle() },
            sheetPeekHeight = 150.dp,
            sheetContent = {
                AnimatedVisibility(visible = !isLandscape) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyListState
                    ) {
                        itemsIndexed(musicList) { index: Int, item: Song ->
                            TrackItem(
                                musicState = musicState,
                                song = item,
                                onClick = { isRunning ->
                                    if (!isRunning) onAudioEvent(PlayerEvent.PlayIndex(index))
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        ) {
            if (!isLandscape) {
                mediaContent(
                    false,
                    visibility,
                    musicState,
                    Modifier
                        .fillMaxSize()
                        .verticalGradientScrim(
                            color = AppTheme.colors.primary,
                            startYPercentage = 1f,
                            endYPercentage = 0f
                        )
                )
            }
        }

    }
    if (isLandscape) {
        mediaContent(
            true,
            visibility,
            musicState,
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
    trackList: SnapshotStateList<String>,
    selectedTrack: String,
    uiState: UiState,
    state: MediaState,
    controllerState: ControllerState,
    musicState: MusicState,
    visibility: Boolean,
    onAudioEvent: (PlayerEvent) -> Unit,
    onVisibility: () -> Unit,
    onTrackChange: (String) -> Unit,
    onBack: () -> Unit
) {
    var trackDialog by rememberSaveable { mutableStateOf(false) }
    var backDialog by rememberSaveable { mutableStateOf(false) }
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
    if (trackDialog) {
        AlertDialogTrack(
            selectedTrack = selectedTrack,
            content = trackList,
            onDismiss = { trackDialog = false },
            onDone = {
                trackDialog = false
                onTrackChange(it)
            })
    }
    if (backDialog) {
        BackAlertDialog(
            onDismiss = {
                backDialog = false
                onBack()
            },
            onResume = {
                backDialog = false
                onAudioEvent(PlayerEvent.Play)
            }
        )
    }
    Column(modifier) {
        AnimatedVisibility(visible = !isLandscape) {
            Column {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )
                PlayerHeader(
                    audioVideo = visibility, onBackPress = {
                        onAudioEvent(PlayerEvent.Pause)
                        backDialog = true
                    },
                    onAudioVideo = onVisibility, more = { trackDialog = true }
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level6))
            }
        }
        AnimatedContent(targetState = visibility, label = "player") { targetState ->
            if (targetState) {
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
                                AppCircularProgressIndicator()
                            }
                        },
                        errorMessage = { error ->
                            Box(Modifier.fillMaxSize(), Alignment.Center) {
                                BodyTexts.Level3(
                                    error.message ?: "",
                                    modifier = Modifier
                                        .background(
                                            Color(0x80808080),
                                            RoundedCornerShape(16.dp)
                                        )
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
                    AppIconButton(
                        onClick = if (isLandscape) exitFullscreen else enterFullscreen,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                RoundedCornerShape(30.dp)
                            )
                    ) {
                        AppIcon(
                            imageVector = if (isLandscape) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                            contentDescription = null, tint = Color.White
                        )
                    }
                }
            } else {
                PlayerImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    trackImageUrl = musicState.currentSong.artworkUri,
                    imageBitmap = state.playerState?.mediaMetadata?.artworkData
                )
            }
        }
        AnimatedVisibility(visible = !isLandscape) {
            Column {
                Spacer(modifier = Modifier.height(AppTheme.spacing.level6))
                TitleTexts.Level1(
                    text = musicState.currentSong.title,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level1)
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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
                        .padding(horizontal = AppTheme.spacing.level2)
                ) {
                    BodyTexts.Level2(text = controllerState.positionMs.asFormattedString())
                    Spacer(modifier = Modifier.weight(1f))
                    BodyTexts.Level2(musicState.duration.asFormattedString())
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level4))
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
                Spacer(modifier = Modifier.height(AppTheme.spacing.level6))
            }
        }
    }
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isLandscape) {
                    exitFullscreen()
                } else {
                    onAudioEvent(PlayerEvent.Pause)
                    backDialog = true
                }
            }
        }
    }
    val onBackPressedDispatcher = activity.onBackPressedDispatcher
    DisposableEffect(onBackPressedDispatcher) {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose { onBackPressedCallback.remove() }
    }
    SideEffect {
        onBackPressedCallback.isEnabled = true
        if (isLandscape) {
            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        }
    }
}

@Composable
fun AlertDialogTrack(
    selectedTrack: String,
    content: List<String>,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit
) {
    val current by remember(selectedTrack) {
        mutableStateOf(selectedTrack)
    }
    AppDialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        AppCard(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    HeadingTexts.Level4(text = stringResource(id = R.string.audio_language))
                }
                content.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AppRadioButton(
                            selected = it == current
                        ) { onDone(it) }
                        BodyTexts.Level2(text = it)
                    }
                }

                Row {
                    AppOutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        BodyTexts.Level2(text = stringResource(id = R.string.cancel))
                    }

                }
            }
        }
    }
}

@Composable
fun BackAlertDialog(
    onDismiss: () -> Unit,
    onResume: () -> Unit
) {
    AppDialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        AppCard(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                HeadingTexts.Level1(text = stringResource(id = R.string.sure_you_want_to_leave))
                HeadingTexts.Level4(text = stringResource(id = R.string.meditation_benefits))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    AppOutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray)
                    ) {
                        CaptionTexts.Level3(text = stringResource(id = R.string.meditate_later))
                    }
                    AppOutlinedButton(
                        onClick = onResume,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = AppTheme.colors.primary)
                    ) {
                        CaptionTexts.Level3(text = stringResource(id = R.string.resume_now))
                    }
                }
            }
        }
    }
}

@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    musicState: MusicState,
    onClick: (Boolean) -> Unit,
    song: Song,
    backgroundColor: Color = Color.Transparent
) {
    val context = LocalContext.current
    val isRunning = musicState.currentSong.id == song.id

    val textColor = if (isRunning) Color.Magenta
    else Color.White


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(isRunning) }
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        AppDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            Column(
                modifier = Modifier.weight(.5f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.Start
            ) {
                BodyTexts.Level1(
                    text = song.title,
                    color = textColor,
                )
                TitleTexts.Level2(
                    text = song.artist,
                    color = textColor,
                )
            }
            AppNetworkImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://dj9n1wsbrvg44.cloudfront.net/tags/Breathing+Tag.png")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .weight(.5f)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}