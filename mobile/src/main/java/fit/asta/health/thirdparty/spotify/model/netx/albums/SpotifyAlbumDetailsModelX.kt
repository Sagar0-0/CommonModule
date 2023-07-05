package fit.asta.health.thirdparty.spotify.model.netx.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalIdsX
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import fit.asta.health.thirdparty.spotify.model.netx.search.TrackListX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyAlbumDetailsModelX(
    @SerializedName("album_type")
    val albumType: String,
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("copyrights")
    val copyrights: List<CopyrightX>,
    @SerializedName("external_ids")
    val externalIds: ExternalIdsX,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("genres")
    val genres: List<String?>,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("label")
    val label: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,
    @SerializedName("total_tracks")
    val totalTracks: Int,
    @SerializedName("tracks")
    val tracks: TrackListX,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable