package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.utils.UiString


data class TestimonialState(
    val id: String = "",
    val type: Int = 0,
    val typeError: UiString = UiString.Empty,
    val title: String = "",
    val titleError: UiString = UiString.Empty,
    val testimonial: String = "",
    val testimonialError: UiString = UiString.Empty,
    val role: String = "",
    val roleError: UiString = UiString.Empty,
    val organization: String = "",
    val organizationError: UiString = UiString.Empty,
    val enableSubmit: Boolean = true
)