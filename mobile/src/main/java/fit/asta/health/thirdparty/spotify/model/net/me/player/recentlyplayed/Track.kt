package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
    val durationMs: Int, // 151363
    @SerializedName("explicit")
    val explicit: Boolean, // true
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsXXXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/6sE0yPUWuIOlqJhsUCvOrg
    @SerializedName("id")
    val id: String, // 6sE0yPUWuIOlqJhsUCvOrg
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // AGORA O 212 VIP EXALA NO CORPO DO PAI
    @SerializedName("popularity")
    val popularity: Int, // 23
    @SerializedName("preview_url")
    val previewUrl: String, // https://p.scdn.co/mp3-preview/aafe98df1cee00802224dabe71e35c077b5b0b45?cid=0598f1a215b7468cad99e23925acf6ef
    @SerializedName("track_number")
    val trackNumber: Int, // 1
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:6sE0yPUWuIOlqJhsUCvOrg
) : Parcelable