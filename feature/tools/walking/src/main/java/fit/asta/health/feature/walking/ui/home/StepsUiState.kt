package fit.asta.health.feature.walking.ui.home

data class StepsUiState(
    var stepCount: Int = 0,
    var caloriesBurned: Int = 0,
    var distance: Float = 0f,
    val recommendDistance: Float = 0f,
    val recommendDuration: Float = 0f
)

data class TargetData(
    val targetDistance: Float = 0f,
    val targetDuration: Float = 0f
)