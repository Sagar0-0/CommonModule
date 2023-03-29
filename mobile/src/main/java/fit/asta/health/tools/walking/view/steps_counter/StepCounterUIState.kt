package fit.asta.health.tools.walking.view.steps_counter

data class StepCounterUIState(
    val calories: Double = 0.0,
    val steps: Int = 0,
    val distance: Int = 0,
    val speed: Float = 0f,
    val time: Long = 0,
    val heartRate:Int=72,
    val weightLoosed:Double=0.0
)