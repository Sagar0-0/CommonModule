package fit.asta.health.feature.sunlight.view.home

sealed class SunlightHomeScreenEvents {
    data object OnStartClick : SunlightHomeScreenEvents()
    data object OnStopClick : SunlightHomeScreenEvents()
}