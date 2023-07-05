package fit.asta.health.thirdparty.spotify.model.net.me.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    @SerializedName("available_markets")
    val availableMarkets: List<String>,
//    @SerializedName("copyrights")
//    val copyrights: List<Any>,
    @SerializedName("description")
    val description: String, // Ranveer Allahbadia aka BeerBiceps brings you #TheRanveerShow. Every episode has been designed to bring you some kind of value add. Life is a never ending self improvement game - whether it's health, career guidance, lifestyle advice or just plain old inspiration... Each episode will charge you up in some way. We deep dive into some of the world's most intelligent, most successful minds in order to mine out the diamonds they've created over the course of their lives. Bollywood stars, athletes, entrepreneurs and all kinds of motivational human beings - featured on India's smartest podcast.
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/shows/6ZcvVBPQ2ToLXEWVbaw59P
    @SerializedName("html_description")
    val htmlDescription: String, // <p>Ranveer Allahbadia aka BeerBiceps brings you #TheRanveerShow. Every episode has been designed to bring you some kind of value add. Life is a never ending self improvement game - whether it&#39;s health, career guidance, lifestyle advice or just plain old inspiration... Each episode will charge you up in some way. We deep dive into some of the world&#39;s most intelligent, most successful minds in order to mine out the diamonds they&#39;ve created over the course of their lives. Bollywood stars, athletes, entrepreneurs and all kinds of motivational human beings - featured on India&#39;s smartest podcast.</p>
    @SerializedName("id")
    val id: String, // 6ZcvVBPQ2ToLXEWVbaw59P
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("is_externally_hosted")
    val isExternallyHosted: Boolean, // false
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("media_type")
    val mediaType: String, // audio
    @SerializedName("name")
    val name: String, // The Ranveer Show
    @SerializedName("publisher")
    val publisher: String, // BeerBiceps
    @SerializedName("total_episodes")
    val totalEpisodes: Int, // 237
    @SerializedName("type")
    val type: String, // show
    @SerializedName("uri")
    val uri: String // spotify:show:6ZcvVBPQ2ToLXEWVbaw59P
) : Parcelable