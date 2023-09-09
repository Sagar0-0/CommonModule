package fit.asta.health.data.scheduler.remote.model

data class TodayData(
    val temperature: String = "22",
    val weatherCode: Int = 0,
    val location: String = "wait",
    val date: String = "01-01-23",
    val slots: List<WeatherData> = emptyList()
)
