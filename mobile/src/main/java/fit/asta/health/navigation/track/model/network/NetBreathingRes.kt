package fit.asta.health.navigation.track.model.network

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
