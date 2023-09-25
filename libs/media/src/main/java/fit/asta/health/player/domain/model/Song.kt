package fit.asta.health.player.domain.model

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
