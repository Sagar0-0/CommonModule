package fit.asta.health.navigation.track.model.net.sleep

import com.google.gson.annotations.SerializedName

data class SlpRatio(
    @SerializedName("deep")
    val deep: Int,
    @SerializedName("delay")
    val delay: Int,
    @SerializedName("disturbed")
    val disturbed: Int,
    @SerializedName("normal")
    val normal: Int
)