package fit.asta.health.tools.exercise.model.network


import com.google.gson.annotations.SerializedName

data class ProgressData(
    @SerializedName("ach")
    val ach: Int,
    @SerializedName("bp")
    val bp: Int,
    @SerializedName("bpm")
    val bpm: Int,
    @SerializedName("cal")
    val cal: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("dur")
    val dur: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("rcm")
    val rcm: Int,
    @SerializedName("tgt")
    val tgt: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("vit")
    val vit: Int
)