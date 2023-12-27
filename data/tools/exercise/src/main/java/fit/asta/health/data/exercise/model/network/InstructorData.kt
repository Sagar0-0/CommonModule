package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName

data class InstructorData(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("code")
    val code: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("music")
    val music: Music,
    @SerializedName("type")
    val type: Int
)