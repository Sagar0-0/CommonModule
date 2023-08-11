package fit.asta.health.navigation.track.model.net.sleep

import com.google.gson.annotations.SerializedName

data class SlpRatio(
    @SerializedName("deep")
    val deep: Float,
    @SerializedName("delay")
    val delay: Float,
    @SerializedName("disturbed")
    val disturbed: Float,
    @SerializedName("normal")
    val normal: Float
)