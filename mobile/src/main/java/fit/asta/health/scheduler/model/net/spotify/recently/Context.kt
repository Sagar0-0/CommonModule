package fit.asta.health.scheduler.model.net.spotify.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.common.ExternalUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class Context(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable