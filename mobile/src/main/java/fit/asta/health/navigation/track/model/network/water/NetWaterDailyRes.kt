package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetWaterDailyRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetWaterDailyData
)

data class NetWaterDailyData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("progress")
    val progress: NetProgress,
    @SerializedName("weather")
    val weather: NetWeather,
    @SerializedName("ratio")
    val ratio: NetRatio,
    @SerializedName("dailyProgress")
    val dailyProgress: NetDailyProgress,
    @SerializedName("beverages")
    val beverages: NetBeveragesList
)

data class NetProgress(
    @SerializedName("percent")
    val percent: Int,
    @SerializedName("achieved")
    val achieved: Double,
    @SerializedName("target")
    val target: Int
)

data class NetWeather(
    @SerializedName("weather")
    val weather: String,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("waterExtra")
    val waterExtra: Int,
    @SerializedName("des")
    val des: String
)