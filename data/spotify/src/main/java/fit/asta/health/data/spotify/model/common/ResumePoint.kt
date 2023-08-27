package fit.asta.health.data.spotify.model.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResumePoint(
    @SerializedName("fully_played")
    val fullyPlayed: Boolean,
    @SerializedName("resume_position_ms")
    val resumePositionMs: Int
) : Parcelable