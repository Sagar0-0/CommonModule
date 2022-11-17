package fit.asta.health.thirdparty.spotify.model.net.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/6sFIWsNpZYqfjUpaCgueju
    @SerializedName("id")
    val id: String, // 6sFIWsNpZYqfjUpaCgueju
    @SerializedName("name")
    val name: String, // Carly Rae Jepsen
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:6sFIWsNpZYqfjUpaCgueju
) : Parcelable