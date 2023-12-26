package fit.asta.health.data.exercise.model.domain.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val mediaItem: MediaItem,
    val mediaUri: Uri,
    val artworkUri: Uri,
    val title: String,
    val artist: String,
    val duration:String
)
