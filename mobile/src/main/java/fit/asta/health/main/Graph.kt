package fit.asta.health.main

sealed class Graph(val route: String) {
    object ROOT : Graph("graph_root")
    object Home : Graph("graph_home")
    object Scheduler : Graph("graph_today_scheduler")
    object Settings : Graph("graph_settings")
    object BreathingTool : Graph("graph_breathing_tool")
    object SleepTool : Graph("graph_sleep_tool")
    object MeditationTool : Graph("graph_meditation_tool")
    object WalkingTool : Graph("graph_walking_tool")
    object ExerciseTool : Graph("graph_exercise_tool")
    object Yoga : Graph("yoga")
    object Workout : Graph("workout")
    object Hiit : Graph("Hiit")
    object Dance : Graph("dance")
    object WaterTool : Graph("graph_water_tool")
    object SunlightTool : Graph("graph_sunlight_tool")
    object Profile : Graph("graph_profile_tool")
    object CreateProfile : Graph("graph_create_profile_tool")
    object Testimonials : Graph("graph_testimonials_tool")
    object Referral : Graph("graph_referral")
    object Wallet : Graph("graph_wallet")
    object Feedback : Graph("graph_feedback")//Always pass fid
}
