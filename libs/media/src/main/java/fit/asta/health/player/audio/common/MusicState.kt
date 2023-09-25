package fit.asta.health.player.audio.common

import android.net.Uri
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.MediaConstants.DEFAULT_DURATION_MS
import fit.asta.health.player.domain.utils.MediaConstants.DEFAULT_MEDIA_ID

data class MusicState(
    val currentSong: Song = Song(
        id = DEFAULT_MEDIA_ID,
        mediaUri = Uri.EMPTY,
        artworkUri = Uri.EMPTY,
        title = "",
        artist = "",
        duration = 0
    ),
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = true,
    val duration: Long = DEFAULT_DURATION_MS
)
