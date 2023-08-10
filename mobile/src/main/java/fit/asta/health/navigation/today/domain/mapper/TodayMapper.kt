package fit.asta.health.navigation.today.domain.mapper

import android.util.Log
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.model.TodaySchedules

fun TodaySchedules.getTodayData(): TodayData {
    Log.d("today", "getTodayData: ${this.data.slot?.slot}")
    return TodayData(
        temperature = this.data.weather.currentWeather.temperature.toString(),
        location = this.data.weather.loc,
        date = this.data.weather.date,
        weatherCode = this.data.weather.currentWeather.weatherCode,
        slots = this.data.slot?.slot ?: emptyList()
    )
}