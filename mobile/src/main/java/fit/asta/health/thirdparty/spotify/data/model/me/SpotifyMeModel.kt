package fit.asta.health.thirdparty.spotify.data.model.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.data.model.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.data.model.common.Followers
import fit.asta.health.thirdparty.spotify.data.model.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyMeModel(
    @SerializedName("country")
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContent,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("followers")
    val followers: Followers,
    @SerializedName("href")
    val href: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("product")
    val product: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable