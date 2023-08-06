package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("loc")
    val location: String,
    @SerializedName("temp")
    val temperature: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("wea")
    val weather: String
)