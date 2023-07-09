package fit.asta.health.tools.exercise.view.video_player

import android.net.Uri

sealed class VideoPlayerEvent(){
    data class PlaySound( val idx: Int,val uri: Uri):VideoPlayerEvent()
}
