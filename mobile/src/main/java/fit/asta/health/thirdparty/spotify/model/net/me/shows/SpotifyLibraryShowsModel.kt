package fit.asta.health.thirdparty.spotify.model.net.me.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryShowsModel(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/me/shows?offset=0&limit=20
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // null
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String, // null
    @SerializedName("total")
    val total: Int // 1
) : Parcelable