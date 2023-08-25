package fit.asta.health.thirdparty.spotify.data.model.library.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.data.model.common.Show
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("show")
    val show: Show
) : Parcelable