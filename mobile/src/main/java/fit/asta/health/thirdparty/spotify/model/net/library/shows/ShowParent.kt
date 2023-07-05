package fit.asta.health.thirdparty.spotify.model.net.library.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Show
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("show")
    val show: Show
) : Parcelable