package fit.asta.health.thirdparty.spotify.model.netx.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.EpisodeX
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeListX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<EpisodeX>,
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