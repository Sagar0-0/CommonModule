package fit.asta.health.tools.exercise.view.video

import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.ShowBuffering
import fit.asta.health.player.jetpack_video.media.SurfaceType
import fit.asta.health.player.jetpack_video.video.ControllerType


data class VideoState(
    val url:String = "https://stream1.asta.fit/Dual_Audio_Video.mkv",
    val surfaceType: SurfaceType = SurfaceType.SurfaceView,
    val resizeMode: ResizeMode = ResizeMode.Fit,
    val keepContentOnPlayerReset:Boolean=false,
    val useArtwork:Boolean=true,
    val showBuffering: ShowBuffering = ShowBuffering.Always,
    val setPlayer:Boolean=true,
    val playWhenReady:Boolean=true,
    val controllerHideOnTouchType:Boolean=true,
    val controllerAutoShow:Boolean=true,
    val controllerType: ControllerType = ControllerType.PlayerControlView,
)