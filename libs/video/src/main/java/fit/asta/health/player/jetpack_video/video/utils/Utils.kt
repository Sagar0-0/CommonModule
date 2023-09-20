package fit.asta.health.player.jetpack_video.video.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SeekParameters
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.video.ControllerType
import java.util.Locale

fun Context.findActivity(): ComponentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    return null
}

val ResizeModes = ResizeMode.values().toList()
val ControllerTypes = ControllerType.values().toList()
val Urls = listOf(
    "https://stream1.asta.fit/Dual_Audio_Video.mkv",
    "https://storage.googleapis.com/downloads.webmproject.org/av1/exoplayer/bbb-av1-480p.mp4",
    "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3",
    "https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv",
    "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
    "https://html5demos.com/assets/dizzy.mp4",
)


/**
 * Switches to selected track.
 *
 * @param trackType The type of track to switch.
 * @param trackIndex The index of the track to switch to, or null to enable the track.
 *
 * if trackIndex is a negative number, the track will be disabled
 * if trackIndex is a valid index, the track will be switched to that index
 */
fun Player.switchTrack(trackType: @C.TrackType Int, trackIndex: Int?) {
    if (trackIndex == null) return
    val trackTypeText = when (trackType) {
        C.TRACK_TYPE_AUDIO -> "audio"
        C.TRACK_TYPE_TEXT -> "subtitle"
        else -> throw IllegalArgumentException("Invalid track type: $trackType")
    }

    if (trackIndex < 0) {
        trackSelectionParameters = trackSelectionParameters
            .buildUpon()
            .setTrackTypeDisabled(trackType, true)
            .build()
    } else {
        val tracks = currentTracks.groups.filter { it.type == trackType }

        if (tracks.isEmpty() || trackIndex >= tracks.size) {
            return
        }

        val trackSelectionOverride = TrackSelectionOverride(tracks[trackIndex].mediaTrackGroup, 0)

        // Override the track selection parameters to force the selection of the specified track.
        trackSelectionParameters = trackSelectionParameters
            .buildUpon()
            .setTrackTypeDisabled(trackType, false)
            .setOverrideForType(trackSelectionOverride)
            .build()
    }
}

/**
 * Sets the seek parameters for the player.
 *
 * @param seekParameters The seek parameters to set.
 */
@UnstableApi
fun Player.setSeekParameters(seekParameters: SeekParameters) {
    when (this) {
        is ExoPlayer -> this.setSeekParameters(seekParameters)
    }
}

/**
 * Seeks to the specified position.
 *
 * @param positionMs The position to seek to, in milliseconds.
 * @param shouldFastSeek Whether to seek to the nearest keyframe.
 */
@UnstableApi
fun Player.seekBack(positionMs: Long, shouldFastSeek: Boolean = false) {
    setSeekParameters(
        SeekParameters.PREVIOUS_SYNC.takeIf { shouldFastSeek } ?: SeekParameters.DEFAULT
    )
    this.seekTo(positionMs)
}

/**
 * Seeks to the specified position.
 *
 * @param positionMs The position to seek to, in milliseconds.
 * @param shouldFastSeek Whether to seek to the nearest keyframe.
 */
@UnstableApi
fun Player.seekForward(positionMs: Long, shouldFastSeek: Boolean = false) {
    setSeekParameters(SeekParameters.NEXT_SYNC.takeIf { shouldFastSeek } ?: SeekParameters.DEFAULT)
    this.seekTo(positionMs)
}

@get:UnstableApi
val Player.audioSessionId: Int
    get() = when (this) {
        is ExoPlayer -> this.audioSessionId
        else -> C.AUDIO_SESSION_ID_UNSET
    }

fun Player.getCurrentTrackIndex(type: @C.TrackType Int): Int {
    return currentTracks.groups
        .filter { it.type == type && it.isSupported }
        .indexOfFirst { it.isSelected }
}

@UnstableApi
fun TrackGroup.getName(trackType: @C.TrackType Int, index: Int): String {
    val format = this.getFormat(0)
    val language = format.language
    val label = format.label
    return buildString {
        if (label != null) {
            append(label)
        }
        if (isEmpty()) {
            if (trackType == C.TRACK_TYPE_TEXT) {
                append("Subtitle Track #${index + 1}")
            } else {
                append("Audio Track #${index + 1}")
            }
        }
        if (language != null && language != "und") {
            append(" - ")
            append(Locale(language).displayLanguage)
        }
    }
}