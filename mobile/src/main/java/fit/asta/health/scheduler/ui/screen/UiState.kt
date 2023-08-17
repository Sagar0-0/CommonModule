package fit.asta.health.scheduler.ui.screen

import fit.asta.health.scheduler.data.db.entity.AlarmEntity


data class UiState(
    val lisOfSchedules: List<AlarmEntity> = emptyList(),
    val scheduleResponse: AlarmEntity
)

