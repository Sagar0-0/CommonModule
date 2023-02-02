package fit.asta.health.testimonials.model.domain

import android.net.Uri
import android.os.Parcelable
import fit.asta.health.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Testimonial(
    val id: String = "",
    val type: TestimonialType = TestimonialType.TEXT,
    val rank: Int = -1,
    val title: String = "",
    val testimonial: String = "",
    val userId: String,
    val user: TestimonialUser,
    val media: List<Media> = listOf()
) : Parcelable

@Parcelize
data class TestimonialUser(
    val name: String = "",
    val org: String = "",
    val role: String = "",
    val url: String = ""
) : Parcelable

@Parcelize
data class Media(
    val name: String = "",
    val title: String = "",
    var url: String = "",
    var localUrl: Uri? = null,
    val error: UiString = UiString.Empty
) : Parcelable