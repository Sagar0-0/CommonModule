package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName

data class Prc(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("selectType")
    val selectType: Boolean,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("values")
    var values: List<Value>
)

data class Value(
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)
