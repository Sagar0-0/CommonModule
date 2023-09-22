package fit.asta.health.player.presentation.screens.player

sealed class PlayerEvent {
    data class SkipTo(val value: Float) : PlayerEvent()
    data object Play : PlayerEvent()
    data object Pause : PlayerEvent()
    data object SkipNext : PlayerEvent()
    data object SkipPrevious : PlayerEvent()
    data object SkipForward : PlayerEvent()
    data object SkipBack : PlayerEvent()
}
