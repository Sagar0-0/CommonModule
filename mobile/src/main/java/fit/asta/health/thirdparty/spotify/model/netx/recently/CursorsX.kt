package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CursorsX(
    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: String
) : Parcelable