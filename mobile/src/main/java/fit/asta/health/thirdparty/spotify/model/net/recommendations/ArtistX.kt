package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/3IpQziA6YwD53PQ5xbwgLF
    @SerializedName("id")
    val id: String, // 3IpQziA6YwD53PQ5xbwgLF
    @SerializedName("name")
    val name: String, // Jóhann Jóhannsson
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:3IpQziA6YwD53PQ5xbwgLF
) : Parcelable