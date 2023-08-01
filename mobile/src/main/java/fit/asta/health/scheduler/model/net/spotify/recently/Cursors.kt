package fit.asta.health.scheduler.model.net.spotify.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cursors(
    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: String
) : Parcelable