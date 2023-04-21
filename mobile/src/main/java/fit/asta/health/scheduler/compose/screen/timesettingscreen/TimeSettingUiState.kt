package fit.asta.health.scheduler.compose.screen.timesettingscreen

import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState

data class TimeSettingUiState(
    val interval: IvlUiState = IvlUiState(),
    val listOfVariantIntervals: List<StatUiState> = emptyList()
)
