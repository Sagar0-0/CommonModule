package fit.asta.health.player.presentation.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.C
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.player.domain.utils.getName
import fit.asta.health.player.media.MediaState
import fit.asta.health.player.media.TimeBar
import fit.asta.health.player.media.rememberControllerState
import kotlinx.coroutines.delay

/**
 * A simple controller, which consists of a play/pause button and a time bar.
 */
@Composable
fun SimpleController(
    mediaState: MediaState,
    modifier: Modifier = Modifier,
) {
    Crossfade(targetState = mediaState.isControllerShowing, modifier, label = "") { isShowing ->
        if (isShowing) {
            val controllerState = rememberControllerState(mediaState)
            var scrubbing by remember { mutableStateOf(false) }
            val hideWhenTimeout = !mediaState.shouldShowControllerIndefinitely && !scrubbing
            var hideEffectReset by remember { mutableStateOf(0) }
            LaunchedEffect(hideWhenTimeout, hideEffectReset) {
                if (hideWhenTimeout) {
                    // hide after 3s
                    delay(3000)
                    mediaState.isControllerShowing = false
                }
            }
//            var visibility by remember {
//                mutableStateOf(false)
//            }
//            if (visibility){
//                mediaState.player?.let {player->
//                    TrackSelectionDialog(
//                        type = C.TRACK_TYPE_AUDIO,
//                        tracks = player.currentTracks,
//                        onTrackSelected = {
//                            player.switchTrack(
//                                C.TRACK_TYPE_AUDIO,
//                                it
//                            )
//                        },
//                        onDismiss = {visibility=false}
//                    )
//                }
//            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x98000000))
            ) {
//                Image( imageVector = Icons.Default.Audiotrack, contentDescription = "",
//                    modifier = Modifier.padding(8.dp)
//                        .clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null,
//                        ) {
//                            hideEffectReset++
//                            visibility=true
//                        }
//                        .align(Alignment.TopEnd),
//                colorFilter = ColorFilter.tint(Color.White))
                AppLocalImage(
                    imageVector = if (controllerState.showPause) Icons.Default.Pause
                    else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .size(52.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            hideEffectReset++
                            controllerState.playOrPause()
                        }
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )

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
                        .height(28.dp)
                        .align(Alignment.BottomCenter),
                    contentPadding = PaddingValues(12.dp),
                    scrubberCenterAsAnchor = true,
                    onScrubStart = { scrubbing = true },
                    onScrubStop = { positionMs ->
                        scrubbing = false
                        controllerState.seekTo(positionMs)
                    }
                )
            }
        }
    }
}


@UnstableApi
@Composable
fun TrackSelectionDialog(
    type: @C.TrackType Int,
    tracks: Tracks,
    onTrackSelected: (trackIndex: Int) -> Unit,
    onDismiss: () -> Unit
) {
    when (type) {
        C.TRACK_TYPE_AUDIO -> {

            val audioTracks = tracks.groups
                .filter { it.type == C.TRACK_TYPE_AUDIO && it.isSupported }

            val trackNames = audioTracks.mapIndexed { index, trackGroup ->
                trackGroup.mediaTrackGroup.getName(type, index)
            }.toTypedArray()

            var selectedTrackIndex = audioTracks
                .indexOfFirst { it.isSelected }.takeIf { it != -1 } ?: audioTracks.size



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
                        trackNames.forEachIndexed { index, item ->
                            Row {
                                AppRadioButton(
                                    selected = selectedTrackIndex == index,
                                    onClick = {
                                        selectedTrackIndex = index
                                        onTrackSelected(index)
                                    },
                                )
                                BodyTexts.Level1(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}