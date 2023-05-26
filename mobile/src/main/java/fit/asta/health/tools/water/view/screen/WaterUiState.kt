package fit.asta.health.tools.water.view.screen

data class WaterUiState(
    val target: Float = 0f,
    val dialogString: String = "",
    val targetAngle: Float = 0f,
    val angle: Float = 0f,
    val start: Boolean = false,
    val showCustomDialog: Boolean = false
)
