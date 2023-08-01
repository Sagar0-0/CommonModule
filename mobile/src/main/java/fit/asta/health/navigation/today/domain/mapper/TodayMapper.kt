package fit.asta.health.navigation.today.domain.mapper

import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.model.TodaySchedules

fun TodaySchedules.getTadayData(): TodayData {
    return TodayData(
        temperature = this.data.weather.currentWeather.temperature.toString(),
        location = this.data.weather.loc,
        date = this.data.weather.date,
        weatherCode = this.data.weather.currentWeather.weatherCode,
        weatherCodeList = this.data.slot.hourly.weathercode,
        temperatureList = this.data.slot.hourly.temperature2m.map { it.toInt() }
    )
}