package fit.asta.health.thirdparty.spotify.model.net.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.model.net.common.Followers
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyMeModel(
    @SerializedName("country")
    val country: String, // string
    @SerializedName("display_name")
    val displayName: String, // string
    @SerializedName("email")
    val email: String, // string
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContent,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("followers")
    val followers: Followers,
    @SerializedName("href")
    val href: String?, // string
    @SerializedName("id")
    val id: String, // string
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("product")
    val product: String, // string
    @SerializedName("type")
    val type: String, // string
    @SerializedName("uri")
    val uri: String // string
) : Parcelable