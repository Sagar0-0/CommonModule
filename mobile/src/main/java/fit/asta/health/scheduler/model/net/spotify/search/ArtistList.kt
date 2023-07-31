package fit.asta.health.scheduler.model.net.spotify.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistList(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val artistList: List<Artist>,
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