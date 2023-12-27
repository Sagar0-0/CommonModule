package fit.asta.health.feature.exercise.nav

sealed class ExerciseName(name: String) {
    data object Yoga : ExerciseName("yoga")
    data object Workout : ExerciseName("workout")
    data object Hiit : ExerciseName("Hiit")
    object Dance : ExerciseName("dance")
}
