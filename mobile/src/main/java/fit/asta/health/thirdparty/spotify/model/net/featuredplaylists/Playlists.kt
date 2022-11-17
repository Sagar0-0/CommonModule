package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlists(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/browse/featured-playlists?country=IN&timestamp=2022-09-12T13%3A09%3A45&offset=0&limit=20
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
    val total: Int // 12
) : Parcelable