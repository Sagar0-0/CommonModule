package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContextX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/223CbIY6UXgDWtX2BzfK0g
    @SerializedName("type")
    val type: String, // playlist
    @SerializedName("uri")
    val uri: String // spotify:playlist:223CbIY6UXgDWtX2BzfK0g
) : Parcelable