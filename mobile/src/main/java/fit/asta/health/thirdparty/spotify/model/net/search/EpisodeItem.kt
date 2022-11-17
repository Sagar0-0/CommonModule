package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeItem(
    @SerializedName("audio_preview_url")
    val audioPreviewUrl: String, // https://p.scdn.co/mp3-preview/918551c320a2a0756e8715fdc897004a136b0837
    @SerializedName("description")
    val description: String, // This episode contains detailed descriptions of a mass shooting that some listeners may find disturbing.A trial is underway in Parkland, Fla., to determine the fate of the gunman who killed 17 people at Marjory Stoneman Douglas High School in 2018.The trial is expected to last for months, forcing people in Parkland to relive the pain of a day they have spent years trying to put behind them.We look back at conversations with some of the survivors of the 2018 shooting at Marjory Stoneman Douglas High School.Guest: Jack Healy, a national correspondent for The New York Times.Background reading: The rare trial of a gunman in a mass shooting has underscored how massacres shatter families and communities over time.As weeks of painful testimony and videos unfold to determine whether the Parkland gunman will face the death penalty, students who spoke out about gun violence have encouraged engagement and changed the national debate.For more information on today’s episode, visit nytimes.com/thedaily. Transcripts of each episode will be made available by the next workday. 
    @SerializedName("duration_ms")
    val durationMs: Int, // 1667736
    @SerializedName("explicit")
    val explicit: Boolean, // true
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/episodes/49y0hF6HQxDPKjWqfiRuH5
    @SerializedName("html_description")
    val htmlDescription: String, // <p>This episode contains detailed descriptions of a mass shooting that some listeners may find disturbing.</p><p>A trial is underway in Parkland, Fla., to determine the fate of the gunman who killed 17 people at Marjory Stoneman Douglas High School in 2018.</p><p>The trial is expected to last for months, forcing people in Parkland to relive the pain of a day they have spent years trying to put behind them.</p><p>We look back at conversations with some of the survivors of the 2018 shooting at Marjory Stoneman Douglas High School.</p><p>Guest: <a href="https://www.nytimes.com/by/jack-healysmid&#61;pc-thedaily" rel="nofollow">Jack Healy</a>, a national correspondent for The New York Times.</p><p>Background reading: </p><ul><li>The rare trial of a gunman in a mass shooting has<a href="https://www.nytimes.com/2022/08/05/us/parkland-shooting-trial-victims-families.html" rel="nofollow"> underscored how massacres shatter families and communities</a> over time.</li><li>As weeks of painful testimony and videos unfold to determine whether the Parkland gunman will face the death penalty, students who spoke out about gun violence<a href="https://www.nytimes.com/2022/08/16/us/parkland-students-march-for-our-lives.html" rel="nofollow"> have encouraged engagement and changed the national debate</a>.</li></ul><p>For more information on today’s episode, visit nytimes.com/thedaily. Transcripts of each episode will be made available by the next workday. </p>
    @SerializedName("id")
    val id: String, // 49y0hF6HQxDPKjWqfiRuH5
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("is_externally_hosted")
    val isExternallyHosted: Boolean, // false
    @SerializedName("is_playable")
    val isPlayable: Boolean, // true
    @SerializedName("language")
    val language: String, // en
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("name")
    val name: String, // The Parkland Students, Four Years Later
    @SerializedName("release_date")
    val releaseDate: String, // 2022-08-31
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("resume_point")
    val resumePoint: ResumePoint,
    @SerializedName("type")
    val type: String, // episode
    @SerializedName("uri")
    val uri: String // spotify:episode:49y0hF6HQxDPKjWqfiRuH5
) : Parcelable