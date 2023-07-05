package fit.asta.health.thirdparty.spotify.model.netx.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistX(
    @SerializedName("collaborative")
    val collaborative: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: OwnerX,
    @SerializedName("primary_color")
    val primaryColor: String?,
    @SerializedName("public")
    val `public`: Boolean,
    @SerializedName("snapshot_id")
    val snapshotId: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable