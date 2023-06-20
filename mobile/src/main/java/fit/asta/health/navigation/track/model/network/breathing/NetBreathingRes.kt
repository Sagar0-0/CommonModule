package fit.asta.health.navigation.track.model.network.breathing

import com.google.gson.annotations.SerializedName

data class NetBreathingRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetData
)

data class NetStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String
)

data class NetData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("prog")
    val progress: NetProgress,
    @SerializedName("wxDtl")
    val weatherDetails: NetWeatherDetails,
    @SerializedName("air")
    val air: NetAirDetail,
    @SerializedName("hDtl")
    val heartDetails: NetHeartDetails,
    @SerializedName("bDtl")
    val bodyDetails: NetBodyDetails,
    @SerializedName("mid")
    val mid: String,
    @SerializedName("mdGph")
    val mdGraphData: NetGraphData,
    @SerializedName("hrtRtGph")
    val heartRateGraph: NetGraphData,
    @SerializedName("bpGph")
    val bloodPressureGraph: NetBloodPressureGraph
)

data class NetProgress(
    @SerializedName("percent")
    val percent: Int,
    @SerializedName("achieved")
    val achieved: Int,
    @SerializedName("target")
    val target: Int
)

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

data class NetAirDetail(
    @SerializedName("sts")
    val status: String,
    @SerializedName("lvl")
    val level: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("meta")
    val airMeta: Meta
)

data class Meta(
    @SerializedName("min")
    val min: String,
    @SerializedName("max")
    val max: String
)

data class NetHeartDetails(
    @SerializedName("bp")
    val bloodPressure: NetBloodPressure,
    @SerializedName("hr")
    val heartRate: NetHeartRate
)

data class NetBloodPressure(
    @SerializedName("mm")
    val mm: Int,
    @SerializedName("hg")
    val hg: Int,
    @SerializedName("sts")
    val status: String,
    @SerializedName("unit")
    val unit: String
)

data class NetHeartRate(
    @SerializedName("rate")
    val heartRate: Int,
    @SerializedName("sts")
    val status: String,
    @SerializedName("unit")
    val unit: String
)

data class NetBodyDetails(
    @SerializedName("in")
    val waterIntake: NetWaterIntake,
    @SerializedName("breath")
    val breath: NetBreath,
    @SerializedName("cal")
    val calories: NetCalories
)

data class NetWaterIntake(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetBreath(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetCalories(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetGraphData(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>?,
    @SerializedName("xData")
    val xData: List<String>?,
    @SerializedName("yData")
    val yData: List<String>?
)

data class NetBloodPressureGraph(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("data")
    val data: List<NetBloodPressureData>
)


data class NetBloodPressureData(
    @SerializedName("name")
    val name: String,
    @SerializedName("xVal")
    val xVal: List<String>,
    @SerializedName("yVal")
    val yVal: List<Int>
)