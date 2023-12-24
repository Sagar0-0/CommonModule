package fit.asta.health.feature.walking.view.home

data class StepsUiState(
    var stepCount: Int = 0,
    var caloriesBurned: Int = 0,
    var distance: Float = 0f,
    val recommendDistance: Float = 0f,
    val recommendDuration: Int = 0,

    )

data class TargetData(
    val targetDistance: Float = 0f,
    val targetDuration: Int = 0
)