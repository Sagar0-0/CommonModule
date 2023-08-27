package fit.asta.health.data.spotify.model.library.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.model.common.Episode
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("episode")
    val episode: Episode
) : Parcelable