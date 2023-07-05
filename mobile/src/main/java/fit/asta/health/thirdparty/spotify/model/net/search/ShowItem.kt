package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowItem(
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
//    @SerializedName("copyrights")
//    val copyrights: List<Any>,
    @SerializedName("description")
    val description: String, // Listen to the wonderful stories of an imaginary city 'Yaad Sheher', narrated by Neelesh Misra at Yaadon ka Idiot Box with Neelesh Misra and Don't forget to follow to the podcast for more stories🌸  📧 Email address  hindistory202271@gmail.com This is a fan made podcast.
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/shows/2vX82sIwqD1pWVTVTfIWtB
    @SerializedName("html_description")
    val htmlDescription: String, // Listen to the wonderful stories of an imaginary city &#39;Yaad Sheher&#39;, narrated by Neelesh Misra at Yaadon ka Idiot Box with Neelesh Misra and Don&#39;t forget to follow to the podcast for more stories&#x1f338;<br/> &#x1f4e7;<br/>Email address <br/>hindistory202271&#64;gmail.com<br/>This is a fan made podcast.
    @SerializedName("id")
    val id: String, // 2vX82sIwqD1pWVTVTfIWtB
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("is_externally_hosted")
    val isExternallyHosted: Boolean, // false
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("media_type")
    val mediaType: String, // audio
    @SerializedName("name")
    val name: String, // NEELESH MISRA
    @SerializedName("publisher")
    val publisher: String, // Rachit Singh Chauhan
    @SerializedName("total_episodes")
    val totalEpisodes: Int, // 176
    @SerializedName("type")
    val type: String, // show
    @SerializedName("uri")
    val uri: String // spotify:show:2vX82sIwqD1pWVTVTfIWtB
) : Parcelable