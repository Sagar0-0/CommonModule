package fit.asta.health.scheduler.data.repo

import fit.asta.health.scheduler.data.api.net.scheduler.Stat
import fit.asta.health.scheduler.data.db.entity.AlarmEntity

interface AlarmUtils {
    fun scheduleAlarm(alarmEntity: AlarmEntity)
    fun schedulerAlarmPostNotification(
        alarmEntity: AlarmEntity,
        isInterval: Boolean,
        interval: Stat?,
        id: Int
    )

    fun scheduleNextAlarm(alarmEntity: AlarmEntity)
    fun scheduleNextIntervalAlarm(alarmEntity: AlarmEntity, variantInterval: Stat)
    fun schedulerAlarmNextPreNotification(
        alarmEntity: AlarmEntity,
        isInterval: Boolean,
        interval: Stat?,
        iD: Int
    )

    fun cancelScheduleAlarm(alarmEntity: AlarmEntity, cancelAllIntervals: Boolean)
    fun snooze(alarmEntity: AlarmEntity)
}