package fit.asta.health.feature.scheduler.doman.usecase

import android.util.Log
import com.google.gson.Gson
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Adv
import fit.asta.health.data.scheduler.remote.net.scheduler.Info
import fit.asta.health.data.scheduler.remote.net.scheduler.Ivl
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.data.scheduler.remote.net.scheduler.Tone
import fit.asta.health.data.scheduler.remote.net.scheduler.Vib
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ASUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi


fun IvlUiState.getInterval(): Ivl {
    return Ivl(
        advancedReminder = Adv(
            status = this.advancedReminder.status,
            time = this.advancedReminder.time
        ),
        snoozeTime = this.snoozeTime,
        endAlarmTime = Time(
            hours = this.endAlarmTime.hours,
            minutes = this.endAlarmTime.minutes
        ),
        statusEnd = this.statusEnd
    )
}

fun ASUiState.getAlarm(
    userId: String,
    alarmId: Long,
    idFromServer: String,
    interval: IvlUiState
): AlarmEntity {
    val newAlarmItem = AlarmEntity(
        status = this.status,
        daysOfWeek = this.week,
        info = Info(
            name = this.alarmName,
            description = this.alarmDescription,
            tag = this.tagName,
            tagId = this.tagId,
            url = this.tagUrl
        ),
        time = Time(
            hours = this.timeHours,
            minutes = this.timeMinutes,
        ),
        interval = interval.getInterval(),
        mode = this.mode,
        important = this.important,
        vibration = Vib(
            pattern = this.vibration,
            status = this.vibrationStatus
        ),
        tone = Tone(
            name = this.toneName,
            type = this.toneType,
            uri = this.toneUri
        ),
        userId = userId,
        idFromServer = idFromServer,
        alarmId = alarmId,
        meta = Meta(
            cDate = this.cDate,
            cBy = this.cBy,
            sync = this.sync,
            uDate = this.uDate
        )
    )
    val map: LinkedHashMap<String, Any> = LinkedHashMap()
    map["id"] = newAlarmItem.idFromServer
    map["uid"] = newAlarmItem.userId
    map["almId"] = newAlarmItem.alarmId
    map["meta"] = newAlarmItem.meta
    map["info"] = newAlarmItem.info
    map["time"] = newAlarmItem.time
    map["sts"] = newAlarmItem.status
    map["imp"] = newAlarmItem.important
    map["mode"] = newAlarmItem.mode
    map["wk"] = newAlarmItem.daysOfWeek
    map["ivl"] = newAlarmItem.interval
    map["vib"] = newAlarmItem.vibration
    map["tone"] = newAlarmItem.tone
    val jsonObject: String? = Gson().toJson(map)
    Log.d("alarm", "getAlarm: $jsonObject")
    return Gson().fromJson(jsonObject, AlarmEntity::class.java)
}

fun AlarmEntity.getIntervalUi(): IvlUiState {
    return IvlUiState(
        advancedReminder = AdvUiState(
            status = this.interval.advancedReminder.status,
            time = this.interval.advancedReminder.time
        ),
        snoozeTime = this.interval.snoozeTime,
        statusEnd = this.interval.statusEnd,
        endAlarmTime = TimeUi(
            hours = this.interval.endAlarmTime.hours,
            minutes = this.interval.endAlarmTime.minutes
        )
    )
}

fun AlarmEntity.getAlarmScreenUi(): ASUiState {
    return ASUiState(
        important = this.important,
        alarmDescription = this.info.description,
        alarmName = this.info.name,
        tagName = this.info.tag,
        tagId = this.info.tagId,
        tagUrl = this.info.url,
        cBy = this.meta.cBy,
        cDate = this.meta.cDate,
        sync = this.meta.sync,
        uDate = this.meta.uDate,
        status = this.status,
        timeHours = this.time.hours,
        timeMinutes = this.time.minutes,
        toneName = this.tone.name,
        toneType = this.tone.type,
        toneUri = this.tone.uri,
        vibration = this.vibration.pattern,
        vibrationStatus = this.vibration.status,
        week = this.daysOfWeek,
        mode = this.mode
    )
}
