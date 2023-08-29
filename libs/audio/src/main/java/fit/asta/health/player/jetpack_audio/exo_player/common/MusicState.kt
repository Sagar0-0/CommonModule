package fit.asta.health.player.jetpack_audio.exo_player.common

import android.net.Uri
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_ALBUM_ID
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_ARTIST_ID
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_DURATION_MS
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_MEDIA_ID

data class MusicState(
    val currentSong: Song = Song(
        id = DEFAULT_MEDIA_ID,
        artistId = DEFAULT_ARTIST_ID,
        albumId = DEFAULT_ALBUM_ID,
        mediaUri = Uri.EMPTY,
        artworkUri = Uri.EMPTY,
        title = "",
        artist = "",
        album = "",
        duration = 0
    ),
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = DEFAULT_DURATION_MS
)
