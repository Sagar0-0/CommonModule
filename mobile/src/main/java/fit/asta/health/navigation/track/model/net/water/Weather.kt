package fit.asta.health.navigation.track.model.net.water

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("des")
    val des: String,
    @SerializedName("temp")
    val temp: Int,
    @SerializedName("waterExtra")
    val waterExtra: Int,
    @SerializedName("weather")
    val weather: String
)