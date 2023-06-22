package fit.asta.health.tools.meditation.view.audio_meditation


sealed class AudioMeditationEvents{
    data class SkipTo(val value:Float): AudioMeditationEvents()
    object Play: AudioMeditationEvents()
    object Pause: AudioMeditationEvents()
    object SkipNext: AudioMeditationEvents()
    object SkipPrevious: AudioMeditationEvents()
    object SkipForward: AudioMeditationEvents()
    object SkipBack: AudioMeditationEvents()
}