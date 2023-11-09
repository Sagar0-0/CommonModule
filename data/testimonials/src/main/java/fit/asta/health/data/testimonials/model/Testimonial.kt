package fit.asta.health.data.testimonials.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Testimonial(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("apv")
    val apv: Boolean = false,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("rank")
    val rank: Int = -1,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("text")
    val testimonial: String = "",
    @SerializedName("uid")
    val userId: String = "",
    @SerializedName("user")
    val user: TestimonialUser = TestimonialUser(),
    @SerializedName("mda")
    val media: List<Media> = listOf()
) : Parcelable

@Parcelize
data class TestimonialUser(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("org")
    val org: String = "",
    @SerializedName("role")
    val role: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable

@Parcelize
data class Media(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @Transient
    var localUrl: Uri? = null,
    @Transient
    val error: UiString = UiString.Empty
) : Parcelable