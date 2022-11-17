package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/album/6OiuCvQifZVGD0DANTN2IL
) : Parcelable