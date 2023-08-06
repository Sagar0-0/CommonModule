package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class HeartRate(
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("sts")
    val sts: String,
    @SerializedName("unit")
    val unit: String
)