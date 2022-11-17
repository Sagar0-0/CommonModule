package fit.asta.health.thirdparty.spotify.model.net.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemTopTrack(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("disc_number")
    val discNumber: Int, // 1
    @SerializedName("duration_ms")
    val durationMs: Int, // 123814
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/5MVzNM3PKjgMYh6SwK17w3
    @SerializedName("id")
    val id: String, // 5MVzNM3PKjgMYh6SwK17w3
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // Precious Memories
    @SerializedName("popularity")
    val popularity: Int, // 17
    @SerializedName("preview_url")
    val previewUrl: String, // https://p.scdn.co/mp3-preview/257a191fde3c98f7d516cb4d521d0b3a1b303f12?cid=0598f1a215b7468cad99e23925acf6ef
    @SerializedName("track_number")
    val trackNumber: Int, // 1
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:5MVzNM3PKjgMYh6SwK17w3
) : Parcelable