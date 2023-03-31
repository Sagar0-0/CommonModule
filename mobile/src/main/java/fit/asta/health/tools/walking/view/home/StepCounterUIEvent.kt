package fit.asta.health.tools.walking.view.home

sealed class StepCounterUIEvent {
    data class ChangeTargetDuration(val input: Float) :StepCounterUIEvent()
    data class ChangeTargetDistance(val input: Float) :StepCounterUIEvent()
    object StartButtonClicked : StepCounterUIEvent()
    object StopButtonClicked : StepCounterUIEvent()
    object PutDataButtonClicked:StepCounterUIEvent()
    object EndButtonClicked : StepCounterUIEvent()
}