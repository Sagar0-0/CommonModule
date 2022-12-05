package fit.asta.health.tools.water.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status


data class NetBeverageRes(
    @SerializedName("data")
    val beverages: List<NetBeverage>,
    @SerializedName("status")
    val status: Status
)

data class NetBeverage(
    @SerializedName("code")
    val code: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ttl")
    val ttl: String
)

