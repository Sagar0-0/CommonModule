package fit.asta.health.data.scheduler.remote.model

import com.google.gson.annotations.SerializedName

data class TodayData(
    val temperature: String = "22",
    val weatherCode: Int = 0,
    val location: String = "wait",
    val date: String = "01-01-23",
    @SerializedName("slot")
    val slots: List<WeatherData> = emptyList()
)
