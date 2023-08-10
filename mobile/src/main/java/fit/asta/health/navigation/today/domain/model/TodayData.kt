package fit.asta.health.navigation.today.domain.model

import fit.asta.health.navigation.today.model.TodaySchedules

data class TodayData(
    val temperature: String = "22",
    val weatherCode: Int = 0,
    val location: String = "wait",
    val date: String = "01-01-23",
    val slots: List<TodaySchedules.Data.Slot> = emptyList()
)
