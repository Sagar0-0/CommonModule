package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName

data class Progress(
    @SerializedName("achieved")
    val achieved: Int,
    @SerializedName("target")
    val target: Int
)