package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class Health(
    @SerializedName("bp")
    val bloodPressure: BloodPressure,
    @SerializedName("hr")
    val heartRate: HeartRate
) {

    data class BloodPressure(
        @SerializedName("hg")
        val hg: Int,
        @SerializedName("mm")
        val mm: Int,
        @SerializedName("sts")
        val sts: String,
        @SerializedName("unit")
        val unit: String
    )

    data class HeartRate(
        @SerializedName("rate")
        val rate: Int,
        @SerializedName("sts")
        val sts: String,
        @SerializedName("unit")
        val unit: String
    )
}