package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("InstructorData")
    val instructorData: List<InstructorData>,
    @SerializedName("MusicData")
    val musicData: MusicData
)