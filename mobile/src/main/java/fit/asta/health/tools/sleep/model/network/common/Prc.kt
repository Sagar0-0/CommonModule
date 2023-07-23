package fit.asta.health.tools.sleep.model.network.common

import com.google.gson.annotations.SerializedName

data class Prc(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("values")
    val values: MutableList<Value>?
)