package fit.asta.health.scheduler.compose.screen

import fit.asta.health.scheduler.model.db.entity.AlarmEntity


data class UiState(
    val lisOfSchedules:List<AlarmEntity> = emptyList() ,
//    val scheduleResponse:AlarmEntity
)

