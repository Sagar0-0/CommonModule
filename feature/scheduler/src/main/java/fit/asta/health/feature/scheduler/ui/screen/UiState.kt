package fit.asta.health.feature.scheduler.ui.screen

import fit.asta.health.data.scheduler.db.entity.AlarmEntity


data class UiState(
    val lisOfSchedules: List<AlarmEntity> = emptyList(),
    val scheduleResponse: AlarmEntity
)

