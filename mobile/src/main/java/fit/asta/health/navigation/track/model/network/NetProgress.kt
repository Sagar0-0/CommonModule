package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetProgress(
    @SerializedName("percent")
    val percent: Int,
    @SerializedName("achieved")
    val achieved: Int,
    @SerializedName("target")
    val target: Int
)
