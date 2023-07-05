package fit.asta.health.thirdparty.spotify.model.net.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @SerializedName("display_name")
    val displayName: String, // Nilesh
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/users/vub235q48345lftinn4rkhuqk
    @SerializedName("id")
    val id: String, // vub235q48345lftinn4rkhuqk
    @SerializedName("type")
    val type: String, // user
    @SerializedName("uri")
    val uri: String // spotify:user:vub235q48345lftinn4rkhuqk
) : Parcelable