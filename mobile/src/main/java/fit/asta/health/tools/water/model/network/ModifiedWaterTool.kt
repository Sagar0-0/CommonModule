package fit.asta.health.tools.water.model.network

import com.google.gson.annotations.SerializedName

data class ModifiedWaterTool(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("tgt")
    val tgt: String,
    @SerializedName("prc")
    val prc: List<Prc>?,
    @SerializedName("wea")
    val wea: Boolean
)

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