package fit.asta.health.tools.exercise.model.network


import com.google.gson.annotations.SerializedName

data class NetPost(
    @SerializedName("bp")
    val bp: Int,
    @SerializedName("bpm")
    val bpm: Int,
    @SerializedName("cal")
    val calories: Int,
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("part")
    val part: List<String>,
    @SerializedName("sty")
    val style: List<String>,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("vit")
    val vit: Int
)