package fit.asta.health.thirdparty.spotify.model.netx.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackParentX(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("track")
    val track: TrackX
) : Parcelable