package fit.asta.health.scheduler.model.net.spotify.library.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.Show
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("show")
    val show: Show
) : Parcelable