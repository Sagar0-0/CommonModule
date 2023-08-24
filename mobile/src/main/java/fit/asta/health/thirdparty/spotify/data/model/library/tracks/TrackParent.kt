package fit.asta.health.thirdparty.spotify.data.model.library.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("track")
    val track: Track
) : Parcelable