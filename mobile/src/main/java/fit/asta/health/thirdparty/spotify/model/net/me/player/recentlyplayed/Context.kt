package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Context(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/223CbIY6UXgDWtX2BzfK0g
    @SerializedName("type")
    val type: String, // playlist
    @SerializedName("uri")
    val uri: String // spotify:playlist:223CbIY6UXgDWtX2BzfK0g
) : Parcelable