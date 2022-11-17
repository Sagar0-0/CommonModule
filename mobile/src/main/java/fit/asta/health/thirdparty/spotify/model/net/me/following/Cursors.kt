package fit.asta.health.thirdparty.spotify.model.net.me.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cursors(
    @SerializedName("after")
    val after: String? // null
) : Parcelable