package fit.asta.health.testimonials.viewmodel.edit

import fit.asta.health.testimonials.model.network.TestimonialType
import fit.asta.health.utils.UiString


data class EditTestimonialState(
    val type: TestimonialType = TestimonialType.TEXT,
    val typeError: UiString = UiString.Empty,
    val title: String = "",
    val titleError: UiString = UiString.Empty,
    val testimonial: String = "",
    val testimonialError: UiString = UiString.Empty,
    val role: String = "",
    val roleError: UiString = UiString.Empty,
    val organization: String = "",
    val organizationError: UiString = UiString.Empty
)