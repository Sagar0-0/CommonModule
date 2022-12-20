package fit.asta.health.testimonials.model.domain

data class Testimonial(
    val id: String = "",
    val type: TestimonialType = TestimonialType.TEXT,
    val rank: Int = -1,
    val title: String = "",
    val testimonial: String = "",
    val userId: String,
    val user: TestimonialUser,
    val media: List<Media>?,
)

enum class TestimonialType(val value: Int) {
    TEXT(0),
    IMAGE(1),
    VIDEO(2);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}

data class TestimonialUser(
    val name: String = "",
    val org: String = "",
    val role: String = "",
    val url: String = ""
)

data class Media(
    val inx: Int = 0,
    val title: String = "",
    val type: MediaType = MediaType.IMAGE,
    var url: String = ""
)

enum class MediaType(val value: Int) {
    IMAGE(0),
    AUDIO(1),
    VIDEO(2);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}