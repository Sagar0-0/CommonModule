package fit.asta.health.thirdparty.spotify.model.netx.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackListX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<TrackX>,
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