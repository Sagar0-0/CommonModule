package fit.asta.health.data.walking.data.source.network.request


import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc


data class PutData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("sType")
    val sType: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("tgt")
    val tgt: Target,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
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
    val dis: Float,
    @SerializedName("unit")
    val unit: String
)
data class Duration(
    @SerializedName("dur")
    val dur: Float,
    @SerializedName("unit")
    val unit: String
)
data class Steps(
    @SerializedName("steps")
    val steps: Int,
    @SerializedName("unit")
    val unit: String
)