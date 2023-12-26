package fit.asta.health.feature.sleep.view.navigation

sealed class SleepToolNavRoutes(val routes: String) {
    data object SleepHomeRoute : SleepToolNavRoutes("sleep-home-screen")
    data object SleepFactorRoute : SleepToolNavRoutes("sleep-factor-screen")
    data object SleepDisturbanceRoute : SleepToolNavRoutes("sleep-disturbance-screen")
    data object SleepJetLagTipsRoute : SleepToolNavRoutes("sleep-jet-lag-tips-screen")
    data object SleepGoalsRoute : SleepToolNavRoutes("sleep-goals-screen")
}