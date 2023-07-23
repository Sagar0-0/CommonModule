package fit.asta.health.tools.exercise.nav

sealed class ExerciseName(name:String){
    object Yoga:ExerciseName("yoga")
    object Workout:ExerciseName("workout")
    object Hiit:ExerciseName("Hiit")
    object Dance:ExerciseName("dance")
}
