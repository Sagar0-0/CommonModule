package fit.asta.health.thirdparty.spotify.model.net.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.PlaylistX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserPlaylistsModel(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/users/b2wpevgtgh78nfhzika5ou1bt/playlists?offset=0&limit=20
    @SerializedName("items")
    val userPlaylistItems: List<PlaylistX>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // https://api.spotify.com/v1/users/b2wpevgtgh78nfhzika5ou1bt/playlists?offset=20&limit=20
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String?, // null
    @SerializedName("total")
    val total: Int // 35
) : Parcelable