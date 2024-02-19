package fit.asta.health.data.water.model.network

import com.google.gson.annotations.SerializedName

data class WaterDetailsData(
    @SerializedName("apv")
    val apv: Boolean,
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