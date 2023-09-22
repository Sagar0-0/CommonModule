package fit.asta.health.tools.exercise.view.video

import android.content.Context
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.presentation.ControllerType


sealed class VideoEvent{
    data class SetResizeMode(val value: ResizeMode): VideoEvent()
    data class SetControllerType(val value: ControllerType): VideoEvent()
    data class UpdateProgress(val value:Float):VideoEvent()
    data class Start(val context: Context): VideoEvent()
    data class Stop(val context: Context): VideoEvent()
}