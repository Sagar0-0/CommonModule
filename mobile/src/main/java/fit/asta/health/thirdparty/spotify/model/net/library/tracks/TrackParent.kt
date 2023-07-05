package fit.asta.health.thirdparty.spotify.model.net.library.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("track")
    val track: Track
) : Parcelable