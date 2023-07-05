package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyAlbumDetailsModel(
    @SerializedName("album_type")
    val albumType: String, // album
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("copyrights")
    val copyrights: List<Copyright>,
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("genres")
    val genres: List<String?>,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy
    @SerializedName("id")
    val id: String, // 4aawyAB9vmqN3uQ7FjRGTy
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("label")
    val label: String, // Mr.305/Polo Grounds Music/RCA Records
    @SerializedName("name")
    val name: String, // Global Warming
    @SerializedName("popularity")
    val popularity: Int, // 50
    @SerializedName("release_date")
    val releaseDate: String, // 2012-11-16
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 18
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:4aawyAB9vmqN3uQ7FjRGTy
) : Parcelable