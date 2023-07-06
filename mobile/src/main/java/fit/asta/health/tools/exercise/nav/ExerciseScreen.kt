package fit.asta.health.tools.exercise.nav


sealed class ExerciseScreen(val route: String) {
    object HomeScreen : ExerciseScreen(route = "exercise_home_screen")
    object Level:ExerciseScreen(route = "exercise_level_screen")
    object Style:ExerciseScreen(route = "exercise_style_screen")
    object BodyParts:ExerciseScreen(route = "exercise_body_parts_screen")
    object BodyStretch:ExerciseScreen(route = "exercise_body_stretch_screen")
    object Goals:ExerciseScreen(route = "exercise_goals_screen")
    object Challenges:ExerciseScreen(route = "exercise_challenges_screen")
    object Equipment:ExerciseScreen(route = "exercise_equipment_screen")
    object Duration:ExerciseScreen(route = "exercise_duration_screen")
    object Music:ExerciseScreen(route = "exercise_music_screen")
}
