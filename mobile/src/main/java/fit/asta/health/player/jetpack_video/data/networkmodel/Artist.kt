package fit.asta.health.player.jetpack_video.data.networkmodel


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)