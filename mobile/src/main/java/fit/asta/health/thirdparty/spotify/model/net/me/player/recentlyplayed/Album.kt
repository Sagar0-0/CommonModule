package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Image
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
    val externalUrls: ExternalUrlsXX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/5l2mNpKokiscks6SEIQnHe
    @SerializedName("id")
    val id: String, // 5l2mNpKokiscks6SEIQnHe
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // PODCAST 003 (2L DE VV)
    @SerializedName("release_date")
    val releaseDate: String, // 2022-08-05
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 7
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:5l2mNpKokiscks6SEIQnHe
) : Parcelable