package fit.asta.health.feature.exercise.view.video_player

import android.net.Uri

sealed class VideoPlayerEvent {
    data class PlaySound(val idx: Int, val uri: Uri) : VideoPlayerEvent()
}
