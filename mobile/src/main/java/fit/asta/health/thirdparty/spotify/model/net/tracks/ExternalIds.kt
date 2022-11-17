package fit.asta.health.thirdparty.spotify.model.net.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String // USUM71703861
) : Parcelable