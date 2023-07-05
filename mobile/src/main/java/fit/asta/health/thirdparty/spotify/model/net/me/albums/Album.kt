package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName("album_type")
    val albumType: String, // single
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("copyrights")
    val copyrights: List<Copyright>,
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/6OiuCvQifZVGD0DANTN2IL
    @SerializedName("id")
    val id: String, // 6OiuCvQifZVGD0DANTN2IL
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("label")
    val label: String, // Ritviz
    @SerializedName("name")
    val name: String, // Jeet
    @SerializedName("popularity")
    val popularity: Int, // 0
    @SerializedName("release_date")
    val releaseDate: String, // 2018-10-29
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 1
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:6OiuCvQifZVGD0DANTN2IL
) : Parcelable