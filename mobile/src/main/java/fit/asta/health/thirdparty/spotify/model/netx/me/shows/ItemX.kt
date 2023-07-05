package fit.asta.health.thirdparty.spotify.model.netx.me.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ShowX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemX(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("show")
    val show: ShowX
) : Parcelable