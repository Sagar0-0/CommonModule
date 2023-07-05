package fit.asta.health.thirdparty.spotify.model.netx.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("followers")
    val followers: FollowersX,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable