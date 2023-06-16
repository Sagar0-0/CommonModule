package fit.asta.health.player.jetpack_audio.presentation.screens.home.discover

sealed class DiscoverEvents {
    data class PlaySound(val isRunning: Boolean, val playWhenReady: Boolean, val idx: Int) :
        DiscoverEvents()
}
