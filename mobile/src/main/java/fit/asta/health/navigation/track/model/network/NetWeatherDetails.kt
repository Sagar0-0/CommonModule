package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetWeatherDetails(
    @SerializedName("wea")
    val weather: NetWeather,
    @SerializedName("vitD")
    val vitaminD: NetVitaminD,
    @SerializedName("dur")
    val duration: NetDuration,
    @SerializedName("exp")
    val exposure: NetExposure
)

data class NetWeather(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("wea")
    val weatherCondition: String,
    @SerializedName("loc")
    val location: String,
    @SerializedName("unit")
    val unit: String
)

data class NetVitaminD(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetDuration(
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetExposure(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)