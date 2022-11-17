package fit.asta.health.thirdparty.spotify.model.net.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyTrackDetailsModel(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @SerializedName("disc_number")
    val discNumber: Int, // 1
    @SerializedName("duration_ms")
    val durationMs: Int, // 207959
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsXXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl
    @SerializedName("id")
    val id: String, // 11dFghVXANMlKmJXsNCbNl
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // Cut To The Feeling
    @SerializedName("popularity")
    val popularity: Int, // 0
    @SerializedName("preview_url")
    val previewUrl: String?, // null
    @SerializedName("track_number")
    val trackNumber: Int, // 1
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:11dFghVXANMlKmJXsNCbNl
) : Parcelable