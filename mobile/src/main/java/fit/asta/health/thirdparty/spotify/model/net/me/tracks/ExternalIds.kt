package fit.asta.health.thirdparty.spotify.model.net.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String // TCABM1381768
) : Parcelable