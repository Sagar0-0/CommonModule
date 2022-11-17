package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName("album_type")
    val albumType: String, // ALBUM
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/5MbsnEpawRoTQpv8IviqDw
    @SerializedName("id")
    val id: String, // 5MbsnEpawRoTQpv8IviqDw
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // Prisoners (Original Motion Picture Soundtrack)
    @SerializedName("release_date")
    val releaseDate: String, // 2013-09-09
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 16
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:5MbsnEpawRoTQpv8IviqDw
) : Parcelable