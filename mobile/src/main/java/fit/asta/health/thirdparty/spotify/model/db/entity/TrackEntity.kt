package fit.asta.health.thirdparty.spotify.model.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.net.tracks.ExternalIds
import fit.asta.health.thirdparty.spotify.model.net.tracks.ExternalUrlsXXX
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FAV_TRACKS_TABLE")
@Parcelize
data class TrackEntity(
    @ColumnInfo(name = "track_album") var trackAlbum: Album?,
    @ColumnInfo(name = "track_artists") var trackArtists: List<ArtistX>?,
    @ColumnInfo(name = "track_available_markets") var trackAvailableMarkets: List<String>?,
    @ColumnInfo(name = "track_disk_number") var trackDiscNumber: Int?,
    @ColumnInfo(name = "track_duration_ms") var trackDurationMs: Int?,
    @ColumnInfo(name = "track_explicit") var trackExplicit: Boolean?,
    @ColumnInfo(name = "track_external_ids") var trackExternalIds: ExternalIds?,
    @ColumnInfo(name = "track_external_urls") var trackExternalUrls: ExternalUrlsXXX?,
    @ColumnInfo(name = "track_href") var trackHref: String?,
    @ColumnInfo(name = "track_is_local") var trackIsLocal: Boolean?,
    @ColumnInfo(name = "track_name") var trackName: String?,
    @ColumnInfo(name = "track_track_popularity") var trackPopularity: Int?,
    @ColumnInfo(name = "track_preview_url") var trackPreviewUrl: String?,
    @ColumnInfo(name = "track_number") var trackTrackNumber: Int?,
    @ColumnInfo(name = "track_type") var trackType: String?,
    @ColumnInfo(name = "track_uri") var trackUri: String?,
    @PrimaryKey var trackId: String,
) : Parcelable