package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.step.StepsDetails

data class Weather(
    @SerializedName("dur")
    val duration: StepsDetails.Duration,
    @SerializedName("exp")
    val exp: CardItem,
    @SerializedName("vitD")
    val vitD: CardItem,
    @SerializedName("wea")
    val weatherData: WeatherData
){

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
}