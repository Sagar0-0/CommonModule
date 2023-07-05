package fit.asta.health.thirdparty.spotify.model.net.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExplicitContent(
    @SerializedName("filter_enabled")
    val filterEnabled: Boolean,
    @SerializedName("filter_locked")
    val filterLocked: Boolean
) : Parcelable