package fit.asta.health.thirdparty.spotify.model.net.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/16aFAW8bLL83CmrOEfDEx0
    @SerializedName("id")
    val id: String, // 16aFAW8bLL83CmrOEfDEx0
    @SerializedName("name")
    val name: String, // The Bilz & Kashif
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:16aFAW8bLL83CmrOEfDEx0
) : Parcelable