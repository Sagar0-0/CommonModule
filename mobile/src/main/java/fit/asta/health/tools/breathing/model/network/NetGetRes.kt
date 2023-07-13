package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetGetRes(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("status")
    val status: Status
)