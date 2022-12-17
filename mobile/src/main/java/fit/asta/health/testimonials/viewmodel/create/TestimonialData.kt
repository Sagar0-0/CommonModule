package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.testimonials.model.domain.Media
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.utils.UiString


data class TestimonialData(
    val id: String = "",
    val type: TestimonialType = TestimonialType.TEXT,
    val title: String = "",
    var titleError: UiString = UiString.Empty,
    val testimonial: String = "",
    var testimonialError: UiString = UiString.Empty,
    val role: String = "",
    var roleError: UiString = UiString.Empty,
    val org: String = "",
    var orgError: UiString = UiString.Empty,
    var media: MutableList<Media> = mutableListOf(),
    val mediaError: UiString = UiString.Empty,
    var enableSubmit: Boolean = false
)