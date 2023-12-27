package fit.asta.health.feature.exercise.view.home

data class ExerciseUiState(
    val targetAngle: Float=0f,
    val progress_angle: Float = 0f,
    val targetValue: Float=0f,
    val consume: Float = 0f,
    val start: Boolean = false,
    var target:Int=0,
    val achieved:Int=0,
    val recommended:Int=90,
    val bp: Int=0,
    val bpm: Int=0,
    val calories: Int=0,
    val duration: Int=0,
    val uid: String="",
    val vit: Int=0,
    val weather: Boolean=false,
)
