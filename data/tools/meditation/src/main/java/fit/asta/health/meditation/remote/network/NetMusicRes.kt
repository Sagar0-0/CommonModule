package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName


data class NetMusicRes(
    @SerializedName("InstructorData")
    val instructorData: List<InstructorData>?,
    @SerializedName("MusicData")
    val musicData: MusicData
)
data class MusicData(
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