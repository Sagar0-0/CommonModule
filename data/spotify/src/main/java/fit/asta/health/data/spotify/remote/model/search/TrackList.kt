package fit.asta.health.data.spotify.remote.model.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.remote.model.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackList(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<Track>,
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