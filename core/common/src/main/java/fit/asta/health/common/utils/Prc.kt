package fit.asta.health.common.utils


import com.google.gson.annotations.SerializedName

data class Prc(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("isMultiSel")
    val isMultiSel: Boolean,
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
    @SerializedName("code")
    val code: String,
    @SerializedName("url")
    val url: String
)
