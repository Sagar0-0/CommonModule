package fit.asta.health.scheduler.model.net.spotify.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserRecentlyPlayedModel(
    @SerializedName("cursors")
    val cursors: Cursors,
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<TrackParent>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String
) : Parcelable