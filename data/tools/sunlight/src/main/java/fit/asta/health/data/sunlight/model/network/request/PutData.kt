package fit.asta.health.data.sunlight.model.network.request

import com.google.gson.annotations.SerializedName

data class PutData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("prc")
    val prc: List<Prc>
)

data class Prc(
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("values")
    val values: List<Values>
)

data class Values(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)