package fit.asta.health.tools.walking.view.home

data class HomeUIState(
    val durationValue: Int = 0,
    val durationTarget: Float = 60f,

    val distance: Int = 0,
    val duration: Int = 0,
    val steps: Int = 0,
    val heartRate: Int = 0,
    val calories: Int = 0,
    val weightLoosed: Double = 0.0,
    val bp: String = "120/80",
    val recommendedSunlight: Int = 600,
    val achievedSunlight: Int = 500,
    val start: Boolean = false
)
