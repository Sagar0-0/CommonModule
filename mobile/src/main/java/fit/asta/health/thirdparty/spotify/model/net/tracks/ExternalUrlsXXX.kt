package fit.asta.health.thirdparty.spotify.model.net.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsXXX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/track/11dFghVXANMlKmJXsNCbNl
) : Parcelable