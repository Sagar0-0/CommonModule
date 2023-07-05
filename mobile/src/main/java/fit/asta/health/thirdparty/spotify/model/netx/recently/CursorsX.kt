package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CursorsX(
    @SerializedName("after")
    val after: String, // 1662988198900
    @SerializedName("before")
    val before: String // 1662901690206
) : Parcelable