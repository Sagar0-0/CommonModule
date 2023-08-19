package fit.asta.health.navigation.track.data.remote.model.water

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("des")
    val des: String,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("waterExtra")
    val waterExtra: Int,
    @SerializedName("weather")
    val weather: String
)