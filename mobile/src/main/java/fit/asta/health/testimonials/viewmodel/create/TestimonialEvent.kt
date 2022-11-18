package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.testimonials.model.domain.TestimonialType


sealed class TestimonialEvent {
    data class OnTypeChange(val type: TestimonialType) : TestimonialEvent()
    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    data class OnRoleChange(val role: String) : TestimonialEvent()
    data class OnOrgChange(val org: String) : TestimonialEvent()
    object OnSubmit : TestimonialEvent()
}