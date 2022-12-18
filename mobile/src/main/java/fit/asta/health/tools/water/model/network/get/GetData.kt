package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class GetData(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)