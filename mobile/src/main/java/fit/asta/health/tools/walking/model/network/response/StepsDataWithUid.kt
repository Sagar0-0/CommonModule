package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName

data class StepsDataWithUid(
    @SerializedName("data")
    val data: UserData,
    @SerializedName("status")
    val status: UserStatus
)
data class UserStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String
)
data class UserData(
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
data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
data class Music(
    @SerializedName("dur")
    val duration: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)