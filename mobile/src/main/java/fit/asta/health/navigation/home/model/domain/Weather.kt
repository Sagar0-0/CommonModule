package fit.asta.health.navigation.home.model.domain

data class Weather(
    val id: String = "",
    val sunRise: String = "",
    val sunSet: String = "",
    val temperature: String = "",
    val location: String = "",
    val date: String = "",
    val imgUrl: String = "",
    val weatherType: WeatherType? = null,
    val air: Air? = null
)

data class Air(
    val windSpeed: Double,
    val pressure: Double,
    val windDirection: Double,
    val humidity: Double,
    val airQuality: String = "",
)

data class SunSlot(
    val id: String = "",
    val time: String = "",
    val temperature: String = "",
    val uvIndex: Double,
    val isScheduled: Boolean
)