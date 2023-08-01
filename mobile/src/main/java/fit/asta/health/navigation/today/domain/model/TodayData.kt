package fit.asta.health.navigation.today.domain.model

data class TodayData(
    val temperature: String = "22",
    val weatherCode: Int = 0,
    val location: String = "wait",
    val date: String = "01-01-23",
    val temperatureList: List<Int> = listOf(23),
    val weatherCodeList: List<Int> = emptyList()
)
