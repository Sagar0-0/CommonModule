package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/72beYOeW2sb2yfcS4JsRvb
    @SerializedName("id")
    val id: String, // 72beYOeW2sb2yfcS4JsRvb
    @SerializedName("name")
    val name: String, // Ritviz
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:72beYOeW2sb2yfcS4JsRvb
) : Parcelable