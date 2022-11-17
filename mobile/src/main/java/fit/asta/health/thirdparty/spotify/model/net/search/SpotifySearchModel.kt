package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifySearchModel(
    @SerializedName("albums")
    val albums: Albums,
    @SerializedName("artists")
    val artists: Artists,
    @SerializedName("episodes")
    val episodes: Episodes,
    @SerializedName("playlists")
    val playlists: Playlists,
    @SerializedName("shows")
    val shows: Shows,
    @SerializedName("tracks")
    val tracks: TracksX
) : Parcelable