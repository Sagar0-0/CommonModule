package fit.asta.health.tools.meditation.view.music


sealed class MusicEvents {
    data class PlaySound(val isRunning: Boolean, val playWhenReady: Boolean, val idx: Int) :
        MusicEvents()
}