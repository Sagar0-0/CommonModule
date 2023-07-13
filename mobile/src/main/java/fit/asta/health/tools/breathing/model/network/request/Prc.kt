package fit.asta.health.tools.breathing.model.network.request


import com.google.gson.annotations.SerializedName

data class Prc(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("values")
    val values: List<Value>
)