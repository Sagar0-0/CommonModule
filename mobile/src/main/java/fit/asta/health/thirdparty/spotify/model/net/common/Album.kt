package fit.asta.health.thirdparty.spotify.model.net.common


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FAV_ALBUMS_TABLE")
@Parcelize
data class Album(
    @ColumnInfo(name = "album_type")
    @SerializedName("album_type")
    val albumType: String, // SINGLE
    @ColumnInfo(name = "artists")
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @ColumnInfo(name = "available_markets")
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @ColumnInfo(name = "external_urls")
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @ColumnInfo(name = "href")
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/3NPqMvOQNpLMvWBdr1Z4kp
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    val id: String, // 3NPqMvOQNpLMvWBdr1Z4kp
    @ColumnInfo(name = "images")
    @SerializedName("images")
    val images: List<Image>,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String, // Precious Memories
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String, // 2021-08-06
    @ColumnInfo(name = "release_date_precision")
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @ColumnInfo(name = "total_tracks")
    @SerializedName("total_tracks")
    val totalTracks: Int, // 1
    @ColumnInfo(name = "type")
    @SerializedName("type")
    val type: String, // album
    @ColumnInfo(name = "uri")
    @SerializedName("uri")
    val uri: String // spotify:album:3NPqMvOQNpLMvWBdr1Z4kp
) : Parcelable