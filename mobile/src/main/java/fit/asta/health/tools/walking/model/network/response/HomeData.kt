package fit.asta.health.tools.walking.model.network.response


import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)
data class Data(
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
    val code: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("prc")
    val prc: List<PrcActivity>,
    @SerializedName("sType")
    val sType: Int,
    @SerializedName("tgt")
    val target: Target,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)
data class PrcActivity(
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("values")
    val values: List<ValueData>
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
data class ValueData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)
