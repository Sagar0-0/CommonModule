package fit.asta.health.tools.sleep.model.network.get

import com.google.gson.annotations.SerializedName

data class ProgressData(
    @SerializedName("ach")
    val ach: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rcm")
    val rcm: Int,
    @SerializedName("tgt")
    val tgt: Int,
    @SerializedName("uid")
    val uid: String
)