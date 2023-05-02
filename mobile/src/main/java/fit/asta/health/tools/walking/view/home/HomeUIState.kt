package fit.asta.health.tools.walking.view.home

data class HomeUIState(
    val distanceTarget: Float = 0f,  // for put request to server
    val durationTarget: Float = 0f,   // for put request

    val appliedAngleDuration: Float=0f,//for slider angle to create canvas
    val appliedAngleDistance: Float=0f,

    val valueDistanceRecommendation: Double=0.0,  // for change progress bar
    val valueDurationRecommendation: Int=0,  // for change progress bar
    val valueDistanceGoal: Double=0.0,     // for change progress bar
    val valueDurationGoal: Int=0,    // for change progress bar

    val distance: Double=0.0,  // for change card view
    val duration: Int = 0,
    val durationProgress:Int = 0,
    val steps: Int = 0,

    val heartRate: Int = 0,
    val calories: Int = 0,
    val weightLoosed: Double = 0.0,
    val bp: String = "120/80",

    val recommendedSunlight: Int = 600,
    val achievedSunlight: Int = 500,

    val start: Boolean = false
)
