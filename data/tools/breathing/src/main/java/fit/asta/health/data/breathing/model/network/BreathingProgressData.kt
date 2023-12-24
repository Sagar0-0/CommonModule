package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class BreathingProgressData(
    @SerializedName("ach")
    val achieved: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rcm")
    val recommend: Int,
    @SerializedName("tgt")
    val target: Int,
    @SerializedName("uid")
    val uid: String
)