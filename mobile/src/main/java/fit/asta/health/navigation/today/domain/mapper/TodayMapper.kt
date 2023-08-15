package fit.asta.health.navigation.today.domain.mapper

import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.model.TodaySchedules
import fit.asta.health.navigation.today.view.utils.Utils
import fit.asta.health.scheduler.compose.naman.WeatherData

fun TodaySchedules.getTodayData(): TodayData {
    return TodayData(
        temperature = this.data.weather.currentWeather.temperature.toString(),
        location = this.data.weather.loc,
        date = this.data.weather.date,
        weatherCode = this.data.weather.currentWeather.weatherCode,
        slots = this.data.slot?.slot?.sortedBy { it.time }?.map {
            val dayAndTime = Utils.getDayAndTime(it.time)
            WeatherData(
                time = dayAndTime.time,
                temperature = "${it.temp}°C",
                uvDetails = "${it.uv} Uv",
                timeSlot = dayAndTime.timeOfDay,
                title = dayAndTime.day
            )
        } ?: emptyList(),
        schedule = this.data.schedule
    )
}