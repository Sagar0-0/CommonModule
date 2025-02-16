package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName

data class MeditationProgressData(
    @SerializedName("ach")
    val ach: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("rcm")
    val rcm: Int,
    @SerializedName("rem")
    val rem: Int,
    @SerializedName("tgt")
    val tgt: Int,
    @SerializedName("uid")
    val uid: String
)