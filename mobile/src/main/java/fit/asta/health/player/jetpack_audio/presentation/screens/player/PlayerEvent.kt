package fit.asta.health.player.jetpack_audio.presentation.screens.player

sealed class PlayerEvent {
    data class SkipTo(val value:Float): PlayerEvent()
    object Play: PlayerEvent()
    object Pause: PlayerEvent()
    object SkipNext: PlayerEvent()
    object SkipPrevious: PlayerEvent()
    object SkipForward: PlayerEvent()
    object SkipBack: PlayerEvent()
}
