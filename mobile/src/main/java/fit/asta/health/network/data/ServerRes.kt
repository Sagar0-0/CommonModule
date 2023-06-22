package fit.asta.health.network.data


import com.google.gson.annotations.SerializedName

data class ServerRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)