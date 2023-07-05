package fit.asta.health.thirdparty.spotify.model.net.library.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Playlist
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