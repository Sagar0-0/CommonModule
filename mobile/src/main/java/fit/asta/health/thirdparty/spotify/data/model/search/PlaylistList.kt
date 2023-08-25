package fit.asta.health.thirdparty.spotify.data.model.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.data.model.common.Playlist
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistList(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Playlist>,
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