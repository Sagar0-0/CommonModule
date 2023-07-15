package fit.asta.health.tools.sunlight.view.home

sealed class SunlightHomeScreenEvents {
    object OnStartClick : SunlightHomeScreenEvents()
    object OnStopClick : SunlightHomeScreenEvents()
}