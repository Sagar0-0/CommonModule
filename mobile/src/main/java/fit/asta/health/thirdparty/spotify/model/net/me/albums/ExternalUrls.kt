package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrls(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/artist/72beYOeW2sb2yfcS4JsRvb
) : Parcelable