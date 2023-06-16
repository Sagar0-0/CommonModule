package fit.asta.health.tools.meditation.model.network


import com.google.gson.annotations.SerializedName

data class Prc(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("values")
    val values: List<Value>
)
data class Value(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)
