package fit.asta.health.data.spotify.remote.model.recently


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