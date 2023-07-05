package fit.asta.health.thirdparty.spotify.model.netx.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.EpisodeX
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeParentX(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("episode")
    val episode: EpisodeX
) : Parcelable