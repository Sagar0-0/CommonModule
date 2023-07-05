package fit.asta.health.thirdparty.spotify.model.netx.library.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ShowX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowParentX(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("show")
    val show: ShowX
) : Parcelable