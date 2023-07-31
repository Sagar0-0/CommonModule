package fit.asta.health.scheduler.model.net.spotify.library.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Playlist
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserPlaylistsModel(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val playlistList: List<Playlist>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("total")
    val total: Int
) : Parcelable