package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.utils.UiString


data class TestimonialState(
    val id: String = "",
    val type: Int = 0,
    val title: String = "",
    var titleError: UiString = UiString.Empty,
    val testimonial: String = "",
    var testimonialError: UiString = UiString.Empty,
    val role: String = "",
    var roleError: UiString = UiString.Empty,
    val org: String = "",
    var orgError: UiString = UiString.Empty,
    val enableSubmit: Boolean = true
)