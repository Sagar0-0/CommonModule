package fit.asta.health.player.jetpack_video.data.networkmodel


import com.google.gson.annotations.SerializedName

data class MediaStream(
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