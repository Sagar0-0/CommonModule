package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class Beverage(
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