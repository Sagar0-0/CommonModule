package fit.asta.health.sunlight.feature.navigation

sealed class SunlightScreens(val route: String) {
    data object SunlightHomeScreen : SunlightScreens(route = "sunlight_home_screen")
    data object SkinConditionScreen : SunlightScreens(route = "sunlight_skin_condition_screen")
    data object SessionResultScreen : SunlightScreens(route = "sunlight_session_result_screen")
    data object HelpAndSuggestionScreen : SunlightScreens(route = "sunlight_help_and_suggestion_screen")

}