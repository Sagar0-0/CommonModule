package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName

data class Weekly(
    @SerializedName("ach")
    val ach: Double,
    @SerializedName("tgt")
    val tgt: Int
)