package fit.asta.health.thirdparty.spotify.model.net.library.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Episode
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("episode")
    val episode: Episode
) : Parcelable