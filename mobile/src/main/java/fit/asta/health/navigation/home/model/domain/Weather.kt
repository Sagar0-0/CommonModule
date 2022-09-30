package fit.asta.health.navigation.home.model.domain

data class Weather(
    val id: String = "",
    val date: String = "",
    val temperature: String = "",
    val location: String = "",
    val sunRise: String = "",
    val sunSet: String = "",
    val url: String = "",
    val weatherUrl: String = "",
    val sunSlots: List<SunSlot>? = null
)

data class SunSlot(
    val id: String = "",
    val time: String = "",
    val temperature: String = ""
)