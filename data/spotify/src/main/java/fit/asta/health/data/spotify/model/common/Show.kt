package fit.asta.health.data.spotify.model.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String,
    @SerializedName("html_description")
    val htmlDescription: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("is_externally_hosted")
    val isExternallyHosted: Boolean,
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("total_episodes")
    val totalEpisodes: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable