package fit.asta.health.player.jetpack_audio.exo_player.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_ALBUM_ID
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_ARTIST_ID
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_MEDIA_ID
import fit.asta.health.player.jetpack_audio.exo_player.util.ALBUM_ID
import fit.asta.health.player.jetpack_audio.exo_player.util.ARTIST_ID
import fit.asta.health.player.jetpack_audio.exo_player.util.buildPlayableMediaItem


internal fun Song.asMediaItem() = buildPlayableMediaItem(
    mediaId = id.toString(),
    artistId = artistId,
    albumId = albumId,
    mediaUri = mediaUri,
    artworkUri = artworkUri,
    title = title,
    artist = artist
)


internal fun MediaItem?.asSong() = Song(
    id = this?.mediaId?.toInt() ?: DEFAULT_MEDIA_ID,
    artistId = this?.mediaMetadata?.extras?.getLong(ARTIST_ID) ?: DEFAULT_ARTIST_ID,
    albumId = this?.mediaMetadata?.extras?.getLong(ALBUM_ID) ?: DEFAULT_ALBUM_ID,
    mediaUri = this?.requestMetadata?.mediaUri.orEmpty(),
    artworkUri = this?.mediaMetadata?.artworkUri.orEmpty(),
    title = this?.mediaMetadata?.title.orEmpty(),
    artist = this?.mediaMetadata?.artist.orEmpty(),
    album = this?.mediaMetadata?.albumTitle.orEmpty(),
    duration = 0
)



private fun Uri?.orEmpty() = this ?: Uri.EMPTY
private fun CharSequence?.orEmpty() = (this ?: "").toString()
