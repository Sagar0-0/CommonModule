package fit.asta.health.thirdparty.spotify.model.net.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryTracksModel(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/me/tracks?offset=0&limit=20
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // https://api.spotify.com/v1/me/tracks?offset=20&limit=20
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String, // null
    @SerializedName("total")
    val total: Int // 187
) : Parcelable