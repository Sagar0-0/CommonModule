package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("disc_number")
    val discNumber: Int, // 1
    @SerializedName("duration_ms")
    val durationMs: Int, // 310680
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsXXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/2dVMQ0MWkOspUA2pLNaag2
    @SerializedName("id")
    val id: String, // 2dVMQ0MWkOspUA2pLNaag2
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // The Candlelight Vigil
    @SerializedName("popularity")
    val popularity: Int, // 0
    @SerializedName("preview_url")
    val previewUrl: String, // https://p.scdn.co/mp3-preview/f57f3a46a1cd2f2cbe4bcb98e87f7718549bcdda?cid=790d4d84ff8441d8bd0def81d60545c4
    @SerializedName("track_number")
    val trackNumber: Int, // 5
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:2dVMQ0MWkOspUA2pLNaag2
) : Parcelable