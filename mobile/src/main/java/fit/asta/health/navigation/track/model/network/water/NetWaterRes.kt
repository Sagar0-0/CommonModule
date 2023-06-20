package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetWaterRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetData
)

data class NetStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
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

data class NetRatio(
    @SerializedName("water")
    val water: Double,
    @SerializedName("juice")
    val juice: Double,
    @SerializedName("drink")
    val drink: Int
)

data class NetDailyProgress(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("xData")
    val xData: List<String>,
    @SerializedName("yData")
    val yData: List<Double>
)

data class NetBeverage(
    @SerializedName("name")
    val name: String,
    @SerializedName("xVal")
    val xVal: List<String>?,
    @SerializedName("yVal")
    val yVal: List<Double>?
)

data class NetBeverages(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("data")
    val data: List<NetBeverage>
)

data class NetData(
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
    val beverages: NetBeverages
)