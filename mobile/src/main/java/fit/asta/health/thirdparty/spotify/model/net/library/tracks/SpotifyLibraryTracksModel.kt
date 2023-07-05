package fit.asta.health.thirdparty.spotify.model.net.library.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryTracksModel(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<TrackParent>,
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