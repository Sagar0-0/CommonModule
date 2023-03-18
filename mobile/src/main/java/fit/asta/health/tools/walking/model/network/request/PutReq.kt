package fit.asta.health.tools.walking.model.network.request

import com.google.gson.annotations.SerializedName


data class PutReq(
    @SerializedName("code")
    val steps: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val weather: Boolean
)
data class Prc(
    @SerializedName("dsc")
    val discretion: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("values")
    val values: List<Value>
)
data class Value(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)
