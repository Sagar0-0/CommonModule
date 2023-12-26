package fit.asta.health.feature.breathing.view.home

import android.content.Context

sealed class UiEvent{
    data class SetTarget(val target: Float) : UiEvent()
    data class SetTargetAngle(val angle: Float) : UiEvent()
    data class SetDNDMode(val value: Boolean) : UiEvent()
    data class Start(val context: Context) : UiEvent()
    data class End(val context: Context) : UiEvent()
    data class GetSheetData(val code: String) : UiEvent()
    data class SetExercise(val exercise: List<String>) : UiEvent()
}
