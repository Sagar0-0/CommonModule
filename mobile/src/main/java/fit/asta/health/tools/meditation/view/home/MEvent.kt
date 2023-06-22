package fit.asta.health.tools.meditation.view.home

import android.content.Context

sealed class MEvent() {
    data class SetLevel(val level: String) : MEvent()
    data class SetLanguage(val language: String) : MEvent()
    data class SetInstructor(val instructor: String) : MEvent()
    data class SetTarget(val target: Float) : MEvent()
    data class SetTargetAngle(val angle: Float) : MEvent()
    data class Start(val context: Context) : MEvent()
    data class End(val context: Context) : MEvent()

}
