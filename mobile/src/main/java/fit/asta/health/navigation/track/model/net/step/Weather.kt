package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.CardItem

data class Weather(
    @SerializedName("dur")
    val duration: StepsDetails.Duration,
    @SerializedName("exp")
    val exp: CardItem,
    @SerializedName("vitD")
    val vitD: CardItem,
    @SerializedName("wea")
    val weatherData: WeatherData
)