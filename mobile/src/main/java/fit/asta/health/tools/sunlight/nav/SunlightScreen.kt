package fit.asta.health.tools.sunlight.nav

sealed class SunlightScreen(val route: String) {
    object SunlightHomeScreen : SunlightScreen(route = "sunlight_home_screen")
    object SkinExposureScreen : SunlightScreen(route = "skin_exposure_screen")
    object SkinColorScreen : SunlightScreen(route = "skin_color_screen")
    object SPFSelectionScreen : SunlightScreen(route = "spf_selection_screen")
    object AgeSelectionScreen : SunlightScreen(route = "age_selection_screen")
}
