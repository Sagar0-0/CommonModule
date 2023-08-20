package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class Weekly(
    @SerializedName("ach")
    val ach: Float,
    @SerializedName("tgt")
    val tgt: Float
)