package fit.asta.health.data.scheduler.util

import fit.asta.health.common.utils.Constants
import fit.asta.health.data.scheduler.local.model.TagEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.model.WeatherData
import fit.asta.health.data.scheduler.remote.net.tag.TagData


fun TodaySchedules.getTodayData(): TodayData {//TODO: Check and Remove mapper
    return TodayData(
        temperature = this.weather.temp.toString(),
        location = this.weather.loc,
        date = this.weather.date,
        weatherCode = this.weather.weatherCode,
        slots = this.slot?.slot?.sortedBy { it.time }?.map {
            val dayAndTime = Constants.getDayAndTime(it.time)
            WeatherData(
                time = dayAndTime.time,
                temperature = "${it.temp}°C",
                uvDetails = "${it.uv} Uv",
                timeSlot = dayAndTime.timeOfDay,
                title = dayAndTime.day,
                dateTime=it.time
            )
        } ?: emptyList(),
        weatherType = this.weather.weather ?: "Sunny",
        message = this.slot?.message
    )
}

fun TagData.toTagEntity(): TagEntity {
    return TagEntity(
        id = this.id,
        uid = this.uid,
        name = this.name,
        url = this.url,
        ttl = this.ttl,
        dsc = this.dsc
    )
}