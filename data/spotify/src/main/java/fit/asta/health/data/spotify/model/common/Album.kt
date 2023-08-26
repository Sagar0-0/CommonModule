package fit.asta.health.data.spotify.model.common


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FAV_ALBUMS_TABLE")
@Parcelize
data class Album(
    @ColumnInfo(name = "album_type")
    @SerializedName("album_type")
    val albumType: String,
    @ColumnInfo(name = "artists")
    @SerializedName("artists")
    val artists: List<Artist>,
    @ColumnInfo(name = "available_markets")
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @ColumnInfo(name = "external_urls")
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @ColumnInfo(name = "href")
    @SerializedName("href")
    val href: String,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "images")
    @SerializedName("images")
    val images: List<Image>,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo(name = "release_date_precision")
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,
    @ColumnInfo(name = "total_tracks")
    @SerializedName("total_tracks")
    val totalTracks: Int,
    @ColumnInfo(name = "type")
    @SerializedName("type")
    val type: String,
    @ColumnInfo(name = "uri")
    @SerializedName("uri")
    val uri: String
) : Parcelable