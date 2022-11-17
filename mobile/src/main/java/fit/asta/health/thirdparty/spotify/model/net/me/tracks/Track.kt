package fit.asta.health.thirdparty.spotify.model.net.me.tracks


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
    val durationMs: Int, // 244493
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsXXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/tracks/3OhyYFXr9EDgQcOW1SqCKS
    @SerializedName("id")
    val id: String, // 3OhyYFXr9EDgQcOW1SqCKS
    @SerializedName("is_local")
    val isLocal: Boolean, // false
    @SerializedName("name")
    val name: String, // Tere Nainon Mein
    @SerializedName("popularity")
    val popularity: Int, // 58
    @SerializedName("preview_url")
    val previewUrl: String, // https://p.scdn.co/mp3-preview/5413a06df757f2ac196fd32c676b1b6a4087ac63?cid=0598f1a215b7468cad99e23925acf6ef
    @SerializedName("track_number")
    val trackNumber: Int, // 5
    @SerializedName("type")
    val type: String, // track
    @SerializedName("uri")
    val uri: String // spotify:track:3OhyYFXr9EDgQcOW1SqCKS
) : Parcelable