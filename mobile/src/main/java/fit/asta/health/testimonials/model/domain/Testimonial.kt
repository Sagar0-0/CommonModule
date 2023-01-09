package fit.asta.health.testimonials.model.domain

import android.net.Uri

data class Testimonial(
    val id: String = "",
    val type: TestimonialType = TestimonialType.TEXT,
    val rank: Int = -1,
    val title: String = "",
    val testimonial: String = "",
    val userId: String,
    val user: TestimonialUser,
    val media: List<Media> = listOf()
)

data class TestimonialUser(
    val name: String = "",
    val org: String = "",
    val role: String = "",
    val url: String = ""
)

data class Media(
    val name: String = "",
    val title: String = "",
    var url: String = "",
    var localUrl: Uri? = null
)