package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize


@Parcelize
data class Episode(
    @SerializedName("audio_preview_url")
    val audioPreviewUrl: String, // https://p.scdn.co/mp3-preview/22179e2be8233637d5661faf943b04b08018a677
    @SerializedName("description")
    val description: String, // Watch our Hindi episodes with Dr. Vineet Aggarwal here:UNTOLD Secrets Of Hinduism - Black Magic & Aghoris - https://youtu.be/vAVB95NfbAkThe Immortal Warrior - Legend of Bhagwan Parshuram - https://youtu.be/QhuLH2yhcKEWatch our English episodes with Dr. Vineet Aggarwal here:Real TRUTH Behind Mahabharata, Ramayana & Spaceships - https://youtu.be/oZH70U5t55MIn this very special Indian history episode, we spoke about the Kalki Avatar, the Vishnupuranas, & black magic secrets of the ancient Indian culture. On the show today, we have with us Dr. Vineet Aggarwal, who is also the author and an expert on Sanatan Dharma, Spirituality, and Hindu Philosophies.Who Is The REAL Kalki Avatar? What is God and evil? What does the Kalyug hold and what are the ancient references which prove human culture & behavior? Differences between the concepts of Hinduism & Buddhism culture, and so much more. Share this with every Indian student. Jai Hind! Learn more about your ad choices. Visit podcastchoices.com/adchoices
    @SerializedName("duration_ms")
    val durationMs: Int, // 5466984
    @SerializedName("explicit")
    val explicit: Boolean, // false
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/episodes/2RKhGWS43xdidFUvrV5UC9
    @SerializedName("html_description")
    val htmlDescription: String, // <p>Watch our Hindi episodes with Dr. Vineet Aggarwal here:</p><p>UNTOLD Secrets Of Hinduism - Black Magic &amp; Aghoris - https://youtu.be/vAVB95NfbAk</p><p>The Immortal Warrior - Legend of Bhagwan Parshuram - https://youtu.be/QhuLH2yhcKE</p><p><br /></p><p>Watch our English episodes with Dr. Vineet Aggarwal here:</p><p>Real TRUTH Behind Mahabharata, Ramayana &amp; Spaceships - https://youtu.be/oZH70U5t55M</p><p><br /></p><p>In this very special Indian history episode, we spoke about the Kalki Avatar, the Vishnupuranas, &amp; black magic secrets of the ancient Indian culture. On the show today, we have with us Dr. Vineet Aggarwal, who is also the author and an expert on Sanatan Dharma, Spirituality, and Hindu Philosophies.</p><p><br /></p><p>Who Is The REAL Kalki Avatar? What is God and evil? What does the Kalyug hold and what are the ancient references which prove human culture &amp; behavior? Differences between the concepts of Hinduism &amp; Buddhism culture, and so much more. Share this with every Indian student. Jai Hind!</p><p> </p><p>Learn more about your ad choices. Visit <a href="https://podcastchoices.com/adchoices" rel="nofollow">podcastchoices.com/adchoices</a></p>
    @SerializedName("id")
    val id: String, // 2RKhGWS43xdidFUvrV5UC9
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
    val name: String, // Who Is The REAL Kalki Avatar? Full Explanation By Hinduism Expert Dr. Vineet | The Ranveer Show 224
    @SerializedName("release_date")
    val releaseDate: String, // 2022-07-31
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String, // day
    @SerializedName("resume_point")
    val resumePoint: ResumePoint,
    @SerializedName("show")
    val show: Show,
    @SerializedName("type")
    val type: String, // episode
    @SerializedName("uri")
    val uri: String // spotify:episode:2RKhGWS43xdidFUvrV5UC9
) : Parcelable