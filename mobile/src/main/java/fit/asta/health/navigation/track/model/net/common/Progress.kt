package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName

data class Progress(
    @SerializedName("achieved")
    val achieved: Float,
    @SerializedName("target")
    val target: Float
)