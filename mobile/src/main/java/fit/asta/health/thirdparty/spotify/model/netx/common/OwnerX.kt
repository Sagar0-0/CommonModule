package fit.asta.health.thirdparty.spotify.model.netx.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnerX(
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable