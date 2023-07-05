package fit.asta.health.thirdparty.spotify.model.netx.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.FollowersX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyMeModelX(
    @SerializedName("country")
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContentX,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("followers")
    val followers: FollowersX,
    @SerializedName("href")
    val href: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("product")
    val product: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable