package fit.asta.health.thirdparty.spotify.model.netx.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyTopTracksModelX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val itemTopTracks: List<TrackX>,
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