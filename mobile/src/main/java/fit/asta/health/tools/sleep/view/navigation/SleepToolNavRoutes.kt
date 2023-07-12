package fit.asta.health.tools.sleep.view.navigation

sealed class SleepToolNavRoutes(val routes: String) {
    object SleepHomeRoute : SleepToolNavRoutes("sleep-home-screen")
    object SleepFactorRoute : SleepToolNavRoutes("sleep-factor-screen")
    object SleepDisturbanceRoute : SleepToolNavRoutes("sleep-disturbance-screen")
    object SleepJetLagTipsRoute : SleepToolNavRoutes("sleep-jet-lag-tips-screen")
    object SleepGoalsRoute : SleepToolNavRoutes("sleep-goals-screen")
}