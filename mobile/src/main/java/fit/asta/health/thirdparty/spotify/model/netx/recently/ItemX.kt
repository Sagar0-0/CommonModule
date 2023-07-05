package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemX(
    @SerializedName("context")
    val context: ContextX,
    @SerializedName("played_at")
    val playedAt: String,
    @SerializedName("track")
    val track: TrackX
) : Parcelable