package fit.asta.health.tools.walking.view.steps_counter
data class StepCounterUIState(
    val initialSteps:Int=0,
    val steps: Int = 0,
    val distance:Int=0,
    val speed :Float=0f,
    val startTime:Long=0,
)