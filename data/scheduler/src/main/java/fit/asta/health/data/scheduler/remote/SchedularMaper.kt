package fit.asta.health.data.scheduler.remote

import fit.asta.health.common.utils.Constants
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.data.scheduler.remote.model.SchedulerGetData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetListData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetTagsList
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.model.WeatherData
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.data.scheduler.remote.net.tag.AstaGetTagsListResponse
import fit.asta.health.data.scheduler.remote.net.tag.Data

fun AstaSchedulerGetResponse.mapToSchedulerGetData(): SchedulerGetData {
    return SchedulerGetData(data = this.data)
}

fun AstaSchedulerGetListResponse.mapToSchedulerGetListData(): SchedulerGetListData {
    return SchedulerGetListData(list = this.data)
}

fun AstaGetTagsListResponse.mapToSchedulerGetTagsList(): SchedulerGetTagsList {
    return SchedulerGetTagsList(customTagList = this.customTagData, list = this.data)
}

fun TodaySchedules.getTodayData(): TodayData {
    return TodayData(
        temperature = this.data.weather.currentWeather.temperature.toString(),
        location = this.data.weather.loc,
        date = this.data.weather.date,
        weatherCode = this.data.weather.currentWeather.weatherCode,
        slots = this.data.slot?.slot?.sortedBy { it.time }?.map {
            val dayAndTime = Constants.getDayAndTime(it.time)
            WeatherData(
                time = dayAndTime.time,
                temperature = "${it.temp}°C",
                uvDetails = "${it.uv} Uv",
                timeSlot = dayAndTime.timeOfDay,
                title = dayAndTime.day
            )
        } ?: emptyList()
    )
}

fun Data.toTagEntity(): TagEntity {
    return TagEntity(
        id = this.id, uid = this.uid, name = this.name, url = this.url
    )
}