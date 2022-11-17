package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResumePoint(
    @SerializedName("fully_played")
    val fullyPlayed: Boolean, // false
    @SerializedName("resume_position_ms")
    val resumePositionMs: Int // 42000
) : Parcelable