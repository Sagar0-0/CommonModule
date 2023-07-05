package fit.asta.health.thirdparty.spotify.model.netx.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifySearchModelX(
    @SerializedName("albums")
    val albums: AlbumListX,
    @SerializedName("artists")
    val artists: ArtistListX,
    @SerializedName("episodes")
    val episodes: EpisodeListX,
    @SerializedName("playlists")
    val playlists: PlaylistListX,
    @SerializedName("shows")
    val shows: ShowListX,
    @SerializedName("tracks")
    val tracks: TrackListX
) : Parcelable