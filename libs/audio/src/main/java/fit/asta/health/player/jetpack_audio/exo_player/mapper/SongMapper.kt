package fit.asta.health.player.jetpack_audio.exo_player.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_MEDIA_ID
import fit.asta.health.player.jetpack_audio.exo_player.util.buildPlayableMediaItem


 fun Song.asMediaItem() = buildPlayableMediaItem(
     mediaId = id.toString(),
     mediaUri = mediaUri,
     artworkUri = artworkUri,
     title = title,
     artist = artist
 )


internal fun MediaItem?.asSong() = Song(
    id = this?.mediaId?.toInt() ?: DEFAULT_MEDIA_ID,
    mediaUri = this?.requestMetadata?.mediaUri.orEmpty(),
    artworkUri = this?.mediaMetadata?.artworkUri.orEmpty(),
    title = this?.mediaMetadata?.title.orEmpty(),
    artist = this?.mediaMetadata?.artist.orEmpty(),
    duration = 0
)



private fun Uri?.orEmpty() = this ?: Uri.EMPTY
private fun CharSequence?.orEmpty() = (this ?: "").toString()
