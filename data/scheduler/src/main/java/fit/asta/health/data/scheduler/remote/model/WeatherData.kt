package fit.asta.health.data.scheduler.remote.model

data class WeatherData(
    val title: String,
    val time: String,
    val temperature: String,
    val uvDetails: String,
    val timeSlot: String,
    val dateTime: String,
)