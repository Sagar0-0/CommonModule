package fit.asta.health.main

sealed class Graph(val route: String) {
    object ROOT : Graph("graph_root")
    object Home : Graph("graph_home")
    object Settings : Graph("graph_settings")
    object BreathingTool : Graph("graph_breathing_tool")
    object SleepTool : Graph("graph_sleep_tool")
    object MeditationTool : Graph("graph_meditation_tool")
    object WalkingTool : Graph("graph_walking_tool")
    object ExerciseTool : Graph("graph_exercise_tool")
    object WaterTool : Graph("graph_water_tool")
    object SunlightTool : Graph("graph_sunlight_tool")
}
