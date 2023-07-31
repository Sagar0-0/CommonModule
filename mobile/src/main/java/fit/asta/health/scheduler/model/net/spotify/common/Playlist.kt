package fit.asta.health.scheduler.model.net.spotify.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    @SerializedName("collaborative")
    val collaborative: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
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