package fit.asta.health.feature.walking.view.home

data class StepsUiState(
    val stepCount: Int = 0,
    val caloriesBurned: Int = 0,
    val distance: Float = 0f,
    val recommendDistance: Float = 0f,
    val recommendDuration: Int = 0,

    )

data class TargetData(
    val targetDistance: Float = 0f,
    val targetDuration: Int = 0
)