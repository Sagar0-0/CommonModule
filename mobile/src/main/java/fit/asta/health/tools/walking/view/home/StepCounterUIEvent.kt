package fit.asta.health.tools.walking.view.home

sealed class StepCounterUIEvent {
    object StartButtonClicked : StepCounterUIEvent()
    object StopButtonClicked : StepCounterUIEvent()
    object PutDataButtonClicked:StepCounterUIEvent()
}