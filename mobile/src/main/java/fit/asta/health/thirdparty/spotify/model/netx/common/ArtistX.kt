package fit.asta.health.thirdparty.spotify.model.netx.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/36RxHbl3Duv6WNJjOGPhV4
    @SerializedName("id")
    val id: String, // 36RxHbl3Duv6WNJjOGPhV4
    @SerializedName("name")
    val name: String, // Morunas
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:36RxHbl3Duv6WNJjOGPhV4
) : Parcelable