package fit.asta.health.tools.walking.view.home

data class HomeUIState(
    val durationValue: Int = 0,
    val durationTarget: Float = 60f,
    val valueDistanceRecommendation: Double=0.0,
    val valueDurationRecommendation: Int=0,
    val valueDistanceGoal: Double=0.0,
    val valueDurationGoal: Int=0,

    val distance: Double=0.0, //change all value there it's used
    val duration: Long = 0,
    val steps: Int = 0,

    val heartRate: Int = 0,
    val calories: Int = 0,
    val weightLoosed: Double = 0.0,
    val bp: String = "120/80",

    val recommendedSunlight: Int = 600,
    val achievedSunlight: Int = 500,

    val start: Boolean = false
)
