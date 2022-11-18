package fit.asta.health.player.exoplayer

import android.support.v4.media.MediaMetadataCompat
import fit.asta.health.player.data.entity.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}