package fit.asta.health.tools.walking.model.network.request


import com.google.gson.annotations.SerializedName


data class PutData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("sType")
    val sType: Int,
    @SerializedName("tgt")
    val tgt: Target,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)

data class Prc(
    @SerializedName("code")
    val code: String?=null,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val ttl: String,
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
data class Target(
    @SerializedName("dis")
    val dis: Distance,
    @SerializedName("dur")
    val dur: Duration,
    @SerializedName("steps")
    val steps: Steps
)
data class Distance(
    @SerializedName("dis")
    val dis: Int,
    @SerializedName("unit")
    val unit: String
)
data class Duration(
    @SerializedName("dur")
    val dur: Int,
    @SerializedName("unit")
    val unit: String
)
data class Steps(
    @SerializedName("steps")
    val steps: Int,
    @SerializedName("unit")
    val unit: String
)