package fit.asta.health.scheduler.compose.screen.timesettingscreen

import fit.asta.health.scheduler.model.net.scheduler.Rep

data class TimeSettingUiState(
    // snooze
    val SnoozeTime: Int = 0,
    // advanced duration
    val AdvancedDuration: Int = 0,
    // duration
    val Duration: Int = 0,
    // repeat
    val Repeat: Rep = Rep(time = 0, unit = "")
)
