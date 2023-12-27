package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetGetStart(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("status")
    val status: Status
)