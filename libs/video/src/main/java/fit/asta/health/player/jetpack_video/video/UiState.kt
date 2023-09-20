package fit.asta.health.player.jetpack_video.video

import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.ShowBuffering
import fit.asta.health.player.jetpack_video.media.SurfaceType



 enum class ControllerType {
    None, Simple, PlayerControlView
}
data class UiState(
    val url: String = "https://stream1.asta.fit/Dual_Audio_Video.mkv",
    val surfaceType: SurfaceType = SurfaceType.SurfaceView,
    val resizeMode: ResizeMode = ResizeMode.Fit,
    val keepContentOnPlayerReset: Boolean = false,
    val useArtwork: Boolean = true,
    val showBuffering: ShowBuffering = ShowBuffering.Always,
    val setPlayer: Boolean = true,
    val playWhenReady: Boolean = true,
    val controllerHideOnTouchType: Boolean = true,
    val controllerAutoShow: Boolean = false,
    val controllerType: ControllerType = ControllerType.PlayerControlView,
)
