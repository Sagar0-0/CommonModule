package fit.asta.health.tools.walking.core.data.source.network.response


import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc

data class HomeData(
    @SerializedName("WalkingRecommendation")
    val walkingRecommendation: WalkingRecommendation,
    @SerializedName("WalkingTool")
    val walkingTool: WalkingTool
)

data class WalkingRecommendation(
    @SerializedName("rcm")
    val recommend: Recommend
)


data class WalkingTool(
    @SerializedName("code")
    val code: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("sType")
    val sType: Int,
    @SerializedName("tgt")
    val target: Target,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)
data class Target(
    @SerializedName("dis")
    val distance: Distance,
    @SerializedName("dur")
    val duration: Duration,
    @SerializedName("steps")
    val steps: Steps
)
data class Recommend(
    @SerializedName("dis")
    val distance: Distance,
    @SerializedName("dur")
    val duration: Duration,
    @SerializedName("steps")
    val steps: Steps
)
data class Distance(
    @SerializedName("dis")
    val distance: Float,
    @SerializedName("unit")
    val unit: String
)
data class Duration(
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("unit")
    val unit: String
)
data class Steps(
    @SerializedName("Steps")
    val steps: Int,
    @SerializedName("unit")
    val unit: String
)
