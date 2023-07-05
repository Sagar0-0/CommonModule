package fit.asta.health.thirdparty.spotify.model.net.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: List<Artist>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @SerializedName("disc_number")
    val discNumber: Int,
    @SerializedName("duration_ms")
    val durationMs: Int,
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_local")
    val isLocal: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("preview_url")
    val previewUrl: String?,
    @SerializedName("track_number")
    val trackNumber: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable