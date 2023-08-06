package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

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