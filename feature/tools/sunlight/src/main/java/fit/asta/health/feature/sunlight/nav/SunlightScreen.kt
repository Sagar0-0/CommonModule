package fit.asta.health.feature.sunlight.nav

sealed class SunlightScreen(val route: String) {
    data object SunlightHomeScreen : SunlightScreen(route = "sunlight_home_screen")
    data object SkinExposureScreen : SunlightScreen(route = "skin_exposure_screen")
    data object SkinColorScreen : SunlightScreen(route = "skin_color_screen")
    data object SPFSelectionScreen : SunlightScreen(route = "spf_selection_screen")
    data object AgeSelectionScreen : SunlightScreen(route = "age_selection_screen")
    data object StartedStateComposable : SunlightScreen(route = "started_state")
}
