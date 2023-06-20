package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

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