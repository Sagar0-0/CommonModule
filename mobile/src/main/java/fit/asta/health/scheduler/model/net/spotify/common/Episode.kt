package fit.asta.health.scheduler.model.net.spotify.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    @SerializedName("audio_preview_url")
    val audioPreviewUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration_ms")
    val durationMs: Int,
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
    @SerializedName("is_playable")
    val isPlayable: Boolean,
    @SerializedName("language")
    val language: String,
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,
    @SerializedName("resume_point")
    val resumePoint: ResumePoint,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Parcelable