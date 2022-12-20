package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.mutableStateListOf
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
    val media: MutableList<Media> = mutableStateListOf(),
    val mediaError: UiString = UiString.Empty,
    var enableSubmit: Boolean = false
)