package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("artists")
    val artists: List<Artist>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("disc_number")
    val discNumber: Int, // 1
    @SerializedName("duration_ms")
    val durationMs: Int, // 85400
    @SerializedName("explicit")
    val explicit: Boolean, // true
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsXXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/6OmhkSOpvYBokMKQxpIGx2
    @SerializedName("id")
    val id: String, // 6OmhkSOpvYBokMKQxpIGx2
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // Global Warming (feat. Sensato)
    @SerializedName("preview_url")
    val previewUrl: String?, // null
    @SerializedName("track_number")
    val trackNumber: Int, // 1
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:6OmhkSOpvYBokMKQxpIGx2
) : Parcelable