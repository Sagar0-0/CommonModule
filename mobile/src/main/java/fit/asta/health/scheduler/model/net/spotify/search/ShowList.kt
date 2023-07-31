package fit.asta.health.scheduler.model.net.spotify.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Show
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowList(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Show>,
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