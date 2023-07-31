package fit.asta.health.scheduler.model.net.spotify.library.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Episode
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("episode")
    val episode: Episode
) : Parcelable