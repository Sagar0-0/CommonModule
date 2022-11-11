package fit.asta.health.testimonials.model.domain

data class Testimonial(
    val id: String = "",
    val name: String = "",
    val code: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = ""
)

enum class TestimonialType(val value: Int) {
    TEXT(0),
    IMAGE(1),
    VIDEO(2);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}