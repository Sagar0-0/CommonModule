package fit.asta.health.player.domain.utils

import androidx.media3.common.C
import androidx.media3.common.Player
import fit.asta.health.player.audio.common.PlaybackState
import fit.asta.health.player.domain.utils.Constants.INVALID_PLAYBACK_STATE_ERROR_MESSAGE
import fit.asta.health.player.domain.utils.MediaConstants.DEFAULT_DURATION_MS

internal fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error(INVALID_PLAYBACK_STATE_ERROR_MESSAGE)
}

internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET } ?: DEFAULT_DURATION_MS
