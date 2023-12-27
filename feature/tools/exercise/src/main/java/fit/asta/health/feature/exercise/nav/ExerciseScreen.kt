package fit.asta.health.feature.exercise.nav


sealed class ExerciseScreen(val route: String) {
    data object HomeScreen : ExerciseScreen(route = "exercise_home_screen")
    data object Level : ExerciseScreen(route = "exercise_level_screen")
    data object Style : ExerciseScreen(route = "exercise_style_screen")
    data object BodyParts : ExerciseScreen(route = "exercise_body_parts_screen")
    data object BodyStretch : ExerciseScreen(route = "exercise_body_stretch_screen")
    data object Goals : ExerciseScreen(route = "exercise_goals_screen")
    data object Challenges : ExerciseScreen(route = "exercise_challenges_screen")
    data object Equipment : ExerciseScreen(route = "exercise_equipment_screen")
    data object Duration : ExerciseScreen(route = "exercise_duration_screen")
    data object Music : ExerciseScreen(route = "exercise_music_screen")
    data object VideoPlayer : ExerciseScreen(route = "exercise_video_player_screen")
    data object Video : ExerciseScreen(route = "exercise_video_screen")
}
