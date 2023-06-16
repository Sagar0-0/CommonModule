package fit.asta.health.tools.meditation.view.home

sealed class MEvent() {
    data class setLevel(val level: String) : MEvent()
    data class setLanguage(val language: String) : MEvent()
    data class setInstructor(val instructor: String) : MEvent()
    data class setTarget(val target: Float) : MEvent()
    data class setTargetAngle(val angle: Float) : MEvent()
    object Start : MEvent()
    object End : MEvent()
}
