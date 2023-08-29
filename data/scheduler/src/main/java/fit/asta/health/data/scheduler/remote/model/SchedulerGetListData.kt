package fit.asta.health.data.scheduler.remote.model

import fit.asta.health.data.scheduler.db.entity.AlarmEntity

data class SchedulerGetListData(val list: List<AlarmEntity>)
