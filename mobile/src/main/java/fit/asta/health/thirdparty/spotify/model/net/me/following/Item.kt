package fit.asta.health.thirdparty.spotify.model.net.me.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("followers")
    val followers: Followers,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/0bYMprIYMBYmNXdeKh7YDQ
    @SerializedName("id")
    val id: String, // 0bYMprIYMBYmNXdeKh7YDQ
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // SARRA
    @SerializedName("popularity")
    val popularity: Int, // 21
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:0bYMprIYMBYmNXdeKh7YDQ
) : Parcelable