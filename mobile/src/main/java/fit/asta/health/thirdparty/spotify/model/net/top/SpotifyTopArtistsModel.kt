package fit.asta.health.thirdparty.spotify.model.net.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyTopArtistsModel(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/me/top/artists
    @SerializedName("items")
    val items: List<ItemTopArtist>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // https://api.spotify.com/v1/me/top/artists?limit=20&offset=20
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String?, // null
    @SerializedName("total")
    val total: Int // 50
) : Parcelable