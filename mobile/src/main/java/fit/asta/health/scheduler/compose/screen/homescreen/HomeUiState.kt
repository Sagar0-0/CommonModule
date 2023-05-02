package fit.asta.health.scheduler.compose.screen.homescreen

import fit.asta.health.scheduler.model.db.entity.AlarmEntity

data class HomeUiState(
    val alarmList: List<AlarmEntity> = emptyList()
)
