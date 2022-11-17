package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @SerializedName("display_name")
    val displayName: String, // Spotify
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/users/spotify
    @SerializedName("id")
    val id: String, // spotify
    @SerializedName("type")
    val type: String, // user
    @SerializedName("uri")
    val uri: String // spotify:user:spotify
) : Parcelable