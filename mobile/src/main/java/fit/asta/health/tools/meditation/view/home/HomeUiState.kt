package fit.asta.health.tools.meditation.view.home

data class HomeUiState(
    val targetAngle: Float=0f,
    val angle: Float = 0f,
    val consume: Float = 0f,
    val start: Boolean = false,
    var target:Int=0,
    val achieved:Int=0,
    val remaining:Int=0,
    val recommended:Int=90,
)
