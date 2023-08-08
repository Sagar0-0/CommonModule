package fit.asta.health.navigation.today.domain.mapper

import android.util.Log
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.model.TodaySchedules

fun TodaySchedules.getTadayData(): TodayData {
    val todayList = ArrayList<TodaySchedules.Data.Slot>()
    val todayListFloat = ArrayList<Float>()
    val tomorrowList = ArrayList<String>()
    val nextTomorrowList = ArrayList<String>()
    val todayListTemp = ArrayList<Double>()
    val tomorrowListTemp = ArrayList<String>()
    val nextTomorrowListTemp = ArrayList<String>()
    this.data.slot?.slot?.forEachIndexed { index, slot ->
        when (index) {
            in 0..5 -> {
                todayList.add(slot)
                todayListTemp.add(slot.temp)
            }

            in 6..12 -> tomorrowList.add(slot.time)
            in 13..17 -> nextTomorrowList.add(slot.time)
        }
    }
    todayListTemp.sort()
    val map: HashMap<Double, Int> = HashMap()
    todayListTemp.forEachIndexed { index, d ->
        map[d] = index
    }
    todayListTemp.sortDescending()
    todayList.forEachIndexed { _, slot ->
        todayListFloat.add(map[slot.temp]?.toFloat() ?: 0f)
    }
    Log.d("today", "getTadayData: temp $todayListTemp,float:$todayListFloat,value:$todayList")
    return TodayData(
        temperature = this.data.weather.currentWeather.temperature.toString(),
        location = this.data.weather.loc,
        date = this.data.weather.date,
        weatherCode = this.data.weather.currentWeather.weatherCode,
        slotTemp = todayListTemp.map { it.toString() },
        slotTime = todayList.map { it.time },
        slotTempFloat = todayListFloat
    )
}