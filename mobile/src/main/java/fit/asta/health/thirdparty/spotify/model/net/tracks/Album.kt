package fit.asta.health.thirdparty.spotify.model.net.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName("album_type")
    val albumType: String, // single
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/0tGPJ0bkWOUmH7MEOR77qc
    @SerializedName("id")
    val id: String, // 0tGPJ0bkWOUmH7MEOR77qc
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // Cut To The Feeling
    @SerializedName("release_date")
    val releaseDate: String, // 2017-05-26
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("total_tracks")
    val totalTracks: Int, // 1
    @SerializedName("type")
    val type: String, // album
    @SerializedName("uri")
    val uri: String // spotify:album:0tGPJ0bkWOUmH7MEOR77qc
) : Parcelable