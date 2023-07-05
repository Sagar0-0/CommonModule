package fit.asta.health.thirdparty.spotify.model.netx.library.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.PlaylistX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserPlaylistsModelX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val playlistList: List<PlaylistX>,
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