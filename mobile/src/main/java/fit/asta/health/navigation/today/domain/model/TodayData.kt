package fit.asta.health.navigation.today.domain.model

import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.ui.naman.WeatherData

data class TodayData(
    val temperature: String = "22",
    val weatherCode: Int = 0,
    val location: String = "wait",
    val date: String = "01-01-23",
    val slots: List<WeatherData> = emptyList(),
    val schedule: List<AlarmEntity> = emptyList()
)
