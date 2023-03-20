package fit.asta.health.tools.walking.view.steps_counter

import fit.asta.health.tools.walking.view.home.StepCounterUIEvent

sealed class StepScreenUIEvent {
     object StartButtonClicked : StepScreenUIEvent()
     object StopButtonClicked : StepScreenUIEvent()
}
