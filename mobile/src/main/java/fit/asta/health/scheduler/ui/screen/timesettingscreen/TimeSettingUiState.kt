package fit.asta.health.scheduler.ui.screen.timesettingscreen

import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.StatUiState

data class TimeSettingUiState(
    val interval: IvlUiState = IvlUiState(),
    val listOfVariantIntervals: List<StatUiState> = emptyList()
)
