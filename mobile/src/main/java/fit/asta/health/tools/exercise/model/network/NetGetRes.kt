package fit.asta.health.tools.exercise.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetGetRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)