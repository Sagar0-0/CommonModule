package fit.asta.health.tools.breathing.view.home

data class UiState(
    val targetAngle: Float=0f,
    val progress_angle: Float = 0f,
    val targetValue: Float=0f,
    val consume: Float = 0f,
    val start: Boolean = false,
    var target:Int=0,
    val achieved:Int=0,
    val recommended:Int=90,
    val uid: String="",
    val weather: Boolean=false,
)
