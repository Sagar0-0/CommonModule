package fit.asta.health.scheduler.model.net.spotify.library.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("track")
    val track: Track
) : Parcelable