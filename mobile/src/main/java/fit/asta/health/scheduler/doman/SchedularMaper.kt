package fit.asta.health.scheduler.doman

import com.google.gson.Gson
import fit.asta.health.scheduler.data.api.net.scheduler.Adv
import fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.data.api.net.scheduler.Info
import fit.asta.health.scheduler.data.api.net.scheduler.Ivl
import fit.asta.health.scheduler.data.api.net.scheduler.Meta
import fit.asta.health.scheduler.data.api.net.scheduler.Rep
import fit.asta.health.scheduler.data.api.net.scheduler.Stat
import fit.asta.health.scheduler.data.api.net.scheduler.Time
import fit.asta.health.scheduler.data.api.net.scheduler.Tone
import fit.asta.health.scheduler.data.api.net.scheduler.Vib
import fit.asta.health.scheduler.data.api.net.scheduler.Wk
import fit.asta.health.scheduler.data.api.net.tag.AstaGetTagsListResponse
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.doman.model.SchedulerGetData
import fit.asta.health.scheduler.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.doman.model.SchedulerGetTagsList
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.ASUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.StatUiState

fun AstaSchedulerGetResponse.mapToSchedulerGetData(): SchedulerGetData {
    return SchedulerGetData(data = this.data)
}

fun AstaSchedulerGetListResponse.mapToSchedulerGetListData(): SchedulerGetListData {
    return SchedulerGetListData(list = this.data)
}

fun AstaGetTagsListResponse.mapToSchedulerGetTagsList(): SchedulerGetTagsList {
    return SchedulerGetTagsList(customTagList = this.customTagData, list = this.data)
}

fun IvlUiState.getInterval():Ivl{
    return Ivl(
        advancedReminder = Adv(
            status = this.advancedReminder.status,
            time = this.advancedReminder.time
        ),
        duration = this.duration,
        isRemainderAtTheEnd = this.isRemainderAtTheEnd,
        repeatableInterval = Rep(
            time = this.repeatableInterval.time,
            unit = this.repeatableInterval.unit
        ),
        snoozeTime = this.snoozeTime,
        staticIntervals = if (this.status && !this.isVariantInterval) {
            this.staticIntervals.map {
                Stat(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            }
        } else emptyList(),
        variantIntervals = if (this.status && this.isVariantInterval) {
            this.variantIntervals.map {
                Stat(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            }
        } else emptyList(),
        isVariantInterval = this.isVariantInterval,
        status = this.status
    )
}

fun ASUiState.getAlarm(
    userId: String,
    alarmId: Int,
    idFromServer: String,
    interval: IvlUiState
): AlarmEntity {
    val newAlarmItem = AlarmEntity(
        status = this.status,
        week = Wk(
            friday = this.friday,
            monday = this.monday,
            saturday = this.saturday,
            sunday = this.sunday,
            thursday = this.thursday,
            tuesday = this.tuesday,
            wednesday = this.wednesday,
            recurring = this.recurring
        ),
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
            midDay = this.timeMidDay,
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
        idFromServer =idFromServer,
        alarmId =alarmId,
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
    map["wk"] = newAlarmItem.week
    map["ivl"] = newAlarmItem.interval
    map["vib"] = newAlarmItem.vibration
    map["tone"] = newAlarmItem.tone
    val jsonObject: String? = Gson().toJson(map)
    return Gson().fromJson(jsonObject, AlarmEntity::class.java)
}

fun AlarmEntity.getIntervalUi(): IvlUiState {
    return IvlUiState(
        status = this.interval.status,
        advancedReminder = AdvUiState(
            status = this.interval.advancedReminder.status,
            time = this.interval.advancedReminder.time
        ),
        duration = this.interval.duration,
        isRemainderAtTheEnd = this.interval.isRemainderAtTheEnd,
        repeatableInterval = RepUiState(
            time = this.interval.repeatableInterval.time,
            unit = this.interval.repeatableInterval.unit
        ),
        staticIntervals = if (this.interval.staticIntervals == null) emptyList()
        else this.interval.staticIntervals!!.map {
            StatUiState(
                hours = it.hours,
                midDay = it.midDay,
                minutes = it.minutes,
                name = it.name,
                id = it.id
            )
        },
        snoozeTime = this.interval.snoozeTime,
        variantIntervals = if (this.interval.variantIntervals == null) emptyList()
        else this.interval.variantIntervals!!.map {
            StatUiState(
                hours = it.hours,
                midDay = it.midDay,
                minutes = it.minutes,
                name = it.name,
                id = it.id
            )
        },
        isVariantInterval = this.interval.isVariantInterval
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
        timeMidDay = this.time.midDay,
        timeMinutes = this.time.minutes,
        toneName = this.tone.name,
        toneType = this.tone.type,
        toneUri = this.tone.uri,
        vibration = this.vibration.pattern,
        vibrationStatus = this.vibration.status,
        friday = this.week.friday,
        saturday = this.week.saturday,
        sunday = this.week.sunday,
        monday = this.week.monday,
        thursday = this.week.thursday,
        tuesday = this.week.tuesday,
        wednesday = this.week.wednesday,
        recurring = this.week.recurring,
        mode = this.mode
    )
}
