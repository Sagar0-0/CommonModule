package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetGetStart(
    @SerializedName("data")
    val `data`: DataXX,
    @SerializedName("status")
    val status: Status
)