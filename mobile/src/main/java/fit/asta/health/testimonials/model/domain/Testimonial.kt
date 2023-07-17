package fit.asta.health.testimonials.model.domain

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.UiString
import fit.asta.health.network.data.Status
import kotlinx.parcelize.Parcelize


data class TestimonialsRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val testimonials: List<Testimonial>
)

data class TestimonialRes(
    @SerializedName("data")
    val testimonial: Testimonial,
    @SerializedName("status")
    val status: Status
)

@Parcelize
data class Testimonial(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("apv")
    val apv: Boolean = false,
    @SerializedName("type")
    val testimonialType: Int = 0,
    @Transient
    val type: TestimonialType = TestimonialType.from(testimonialType),
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