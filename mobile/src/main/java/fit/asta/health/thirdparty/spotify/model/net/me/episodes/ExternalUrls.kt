package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrls(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/episode/2RKhGWS43xdidFUvrV5UC9
) : Parcelable