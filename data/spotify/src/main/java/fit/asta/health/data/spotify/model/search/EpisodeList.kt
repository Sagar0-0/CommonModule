package fit.asta.health.data.spotify.model.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.model.common.Episode
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeList(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Episode>,
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