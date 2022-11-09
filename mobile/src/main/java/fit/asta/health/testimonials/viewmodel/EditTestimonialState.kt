package fit.asta.health.testimonials.viewmodel


data class EditTestimonialState(
    val type: Int = -1,
    val typeError: String? = null,
    val title: String = "",
    val titleError: String? = null,
    val subTitle: String = "",
    val subTitleError: String? = null,
    val testimonial: String = "",
    val testimonialError: String? = null,
    val role: String = "",
    val roleError: String? = null,
    val organization: String = "",
    val organizationError: String? = null
)