package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg
    @SerializedName("id")
    val id: String, // 0TnOYISbd1XYRBk9myaseg
    @SerializedName("name")
    val name: String, // Pitbull
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:0TnOYISbd1XYRBk9myaseg
) : Parcelable