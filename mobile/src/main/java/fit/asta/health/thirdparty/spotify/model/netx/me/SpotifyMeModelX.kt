package fit.asta.health.thirdparty.spotify.model.netx.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyMeModelX(
    @SerializedName("country")
    val country: String, // string
    @SerializedName("display_name")
    val displayName: String, // string
    @SerializedName("email")
    val email: String, // string
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContentX,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("followers")
    val followers: FollowersX,
    @SerializedName("href")
    val href: String?, // string
    @SerializedName("id")
    val id: String, // string
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("product")
    val product: String, // string
    @SerializedName("type")
    val type: String, // string
    @SerializedName("uri")
    val uri: String // string
) : Parcelable