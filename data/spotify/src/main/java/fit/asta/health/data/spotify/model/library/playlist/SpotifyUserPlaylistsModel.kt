package fit.asta.health.data.spotify.model.library.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.model.common.Playlist
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