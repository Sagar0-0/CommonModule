package fit.asta.health.player.jetpack_video.video.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.video.ControllerType

fun Context.findActivity(): ComponentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    return null
}
val ResizeModes = ResizeMode.values().toList()
val ControllerTypes = ControllerType.values().toList()
val Urls = listOf(
    "https://stream1.asta.fit/Dual_Audio_Video.mkv",
    "https://storage.googleapis.com/downloads.webmproject.org/av1/exoplayer/bbb-av1-480p.mp4",
    "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3",
    "https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv",
    "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
    "https://html5demos.com/assets/dizzy.mp4",
)