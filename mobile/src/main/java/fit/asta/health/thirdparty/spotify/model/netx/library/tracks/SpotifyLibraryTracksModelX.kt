package fit.asta.health.thirdparty.spotify.model.netx.library.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryTracksModelX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<TrackParentX>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("total")
    val total: Int
) : Parcelable