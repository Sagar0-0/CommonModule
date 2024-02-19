package fit.asta.health.sunlight.feature.event

sealed interface SunlightHomeEvent {
    data class OnStartTimer(val time: Long) : SunlightHomeEvent
    data object OnStopTimer : SunlightHomeEvent
    data object OnPause : SunlightHomeEvent
    data object OnResume : SunlightHomeEvent

}