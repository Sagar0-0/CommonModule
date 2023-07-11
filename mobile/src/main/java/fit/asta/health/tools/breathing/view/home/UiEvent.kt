package fit.asta.health.tools.breathing.view.home

import android.content.Context

sealed class UiEvent{
    data class SetMusic(val music: String) : UiEvent()
    data class SetGoals(val goals: List<String>) : UiEvent()
    data class SetExercise(val exercise: List<String>) : UiEvent()
    data class SetLevel(val level: String) : UiEvent()
    data class SetBreakTime(val time: String) : UiEvent()
    data class SetPace(val pace: String) : UiEvent()
    data class SetLanguage(val language: String) : UiEvent()
    data class SetInstructor(val instructor: String) : UiEvent()
    data class SetTarget(val target: Float) : UiEvent()
    data class SetTargetAngle(val angle: Float) : UiEvent()
    data class Start(val context: Context) : UiEvent()
    data class End(val context: Context) : UiEvent()
}
