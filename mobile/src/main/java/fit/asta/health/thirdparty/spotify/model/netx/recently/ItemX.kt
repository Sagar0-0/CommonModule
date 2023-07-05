package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemX(
    @SerializedName("context")
    val context: ContextX,
    @SerializedName("played_at")
    val playedAt: String, // 2022-09-12T13:09:58.900Z
    @SerializedName("track")
    val track: TrackX
) : Parcelable