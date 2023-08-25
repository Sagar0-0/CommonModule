package fit.asta.health.thirdparty.spotify.data.model.common


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FAV_TRACKS_TABLE")
@Parcelize
data class Track(
    @ColumnInfo(name = "track_album")
    @SerializedName("album")
    val album: Album,
    @ColumnInfo(name = "track_artists")
    @SerializedName("artists")
    val artists: List<Artist>,
    @ColumnInfo(name = "track_available_markets")
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @ColumnInfo(name = "track_disk_number")
    @SerializedName("disc_number")
    val discNumber: Int,
    @ColumnInfo(name = "track_duration_ms")
    @SerializedName("duration_ms")
    val durationMs: Int,
    @ColumnInfo(name = "track_explicit")
    @SerializedName("explicit")
    val explicit: Boolean,
    @ColumnInfo(name = "track_external_ids")
    @SerializedName("external_ids")
    val externalIds: ExternalIds,
    @ColumnInfo(name = "track_external_urls")
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @ColumnInfo(name = "track_href")
    @SerializedName("href")
    val href: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "track_is_local")
    @SerializedName("is_local")
    val isLocal: Boolean,
    @ColumnInfo(name = "track_name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "track_track_popularity")
    @SerializedName("popularity")
    val popularity: Int,
    @ColumnInfo(name = "track_preview_url")
    @SerializedName("preview_url")
    val previewUrl: String?,
    @ColumnInfo(name = "track_number")
    @SerializedName("track_number")
    val trackNumber: Int,
    @ColumnInfo(name = "track_type")
    @SerializedName("type")
    val type: String,
    @ColumnInfo(name = "track_uri")
    @SerializedName("uri")
    val uri: String
) : Parcelable