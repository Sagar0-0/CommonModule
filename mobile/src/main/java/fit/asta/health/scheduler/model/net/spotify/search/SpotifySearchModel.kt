package fit.asta.health.scheduler.model.net.spotify.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifySearchModel(
    @SerializedName("albums")
    val albums: AlbumList,
    @SerializedName("artists")
    val artists: ArtistList,
    @SerializedName("episodes")
    val episodes: EpisodeList,
    @SerializedName("playlists")
    val playlists: PlaylistList,
    @SerializedName("shows")
    val shows: ShowList,
    @SerializedName("tracks")
    val tracks: TrackList
) : Parcelable