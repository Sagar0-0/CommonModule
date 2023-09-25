package fit.asta.health.player.presentation

import android.content.Context
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.ShowBuffering

import fit.asta.health.player.media.SurfaceType

sealed class UiEvent{
    data class SetUrl(val value:String): UiEvent()
    data class SetSurfaceType(val value: SurfaceType): UiEvent()
    data class SetResizeMode(val value: ResizeMode): UiEvent()
    data class KeepContentOnPlayerReset(val value:Boolean): UiEvent()
    data class SetUseArtwork(val value:Boolean): UiEvent()
    data class SetShowBuffering(val value: ShowBuffering): UiEvent()
    data class SetPlayer(val value:Boolean): UiEvent()
    data class PlayWhenReady(val value:Boolean): UiEvent()
    data class SetControllerType(val value: ControllerType): UiEvent()
    data class ControllerHideOnTouchType(val value:Boolean): UiEvent()
    data class ControllerAutoShow(val value:Boolean): UiEvent()
    data class Start(val context: Context): UiEvent()
    data class Stop(val context: Context): UiEvent()
}
