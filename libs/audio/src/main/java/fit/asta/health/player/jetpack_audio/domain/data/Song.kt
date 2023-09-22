package fit.asta.health.player.jetpack_audio.domain.data

import android.net.Uri

data class Song(
    val id: Int,
    val mediaUri: Uri,
    val artworkUri: Uri,
    val title: String,
    val artist: String,
    val duration: Int,
    val audioList: List<String> = emptyList()
)
