package fit.asta.health.data.spotify.remote.model.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.remote.model.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackParent(
    @SerializedName("context")
    val context: Context,
    @SerializedName("played_at")
    val playedAt: String,
    @SerializedName("track")
    val track: Track
) : Parcelable