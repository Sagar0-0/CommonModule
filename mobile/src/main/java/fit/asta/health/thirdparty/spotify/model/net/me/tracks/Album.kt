package fit.asta.health.thirdparty.spotify.model.net.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize


@Parcelize
data class Album(
    @SerializedName("album_type")
    val albumType: String, // album
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/7nsHM24bHFNLBcx5frV8mF
    @SerializedName("id")
    val id: String, // 7nsHM24bHFNLBcx5frV8mF
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("name")
    val name: String, // The Trinity
    @SerializedName("release_date")
    val releaseDate: String, // 2013-04-23
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 11
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:7nsHM24bHFNLBcx5frV8mF
) : Parcelable