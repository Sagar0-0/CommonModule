package fit.asta.health.scheduler.model.doman

import com.google.gson.Gson
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ASUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.doman.model.SchedulerGetData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetTagsList
import fit.asta.health.scheduler.model.net.scheduler.Adv
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.Info
import fit.asta.health.scheduler.model.net.scheduler.Ivl
import fit.asta.health.scheduler.model.net.scheduler.Meta
import fit.asta.health.scheduler.model.net.scheduler.Rep
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.model.net.scheduler.Tone
import fit.asta.health.scheduler.model.net.scheduler.Vib
import fit.asta.health.scheduler.model.net.scheduler.Wk
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse

fun AstaSchedulerGetResponse.mapToSchedulerGetData(): SchedulerGetData {
    return SchedulerGetData(data = this.data)
}
fun AstaSchedulerGetListResponse.mapToSchedulerGetListData():SchedulerGetListData{
    return SchedulerGetListData(list =this.data)
}
fun AstaGetTagsListResponse.mapToSchedulerGetTagsList():SchedulerGetTagsList{
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
fun ASUiState.getAlarm(userId:String,alarmId:Int,idFromServer:String,interval:IvlUiState):AlarmEntity{
    val newAlarmItem= AlarmEntity(
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
            name = this.alarm_name,
            description = this.alarm_description,
            tag = this.tag_name,
            tagId = this.tagId,
            url = this.tag_url
        ),
        time = Time(
            hours = this.time_hours,
            minutes = this.time_minutes,
            midDay = this.time_midDay,
        ),
        interval = interval.getInterval(),
        mode = this.mode,
        important = this.important,
        vibration = Vib(
            percentage = this.vibration_percentage,
            status = this.vibration_status
        ),
        tone = Tone(
            name = this.tone_name,
            type = this.tone_type,
            uri = this.tone_uri
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