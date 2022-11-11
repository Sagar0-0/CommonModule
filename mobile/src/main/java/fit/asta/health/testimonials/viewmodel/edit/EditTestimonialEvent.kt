package fit.asta.health.testimonials.viewmodel.edit

import fit.asta.health.testimonials.model.network.TestimonialType


sealed class EditTestimonialEvent {
    data class OnTypeChange(val type: TestimonialType) : EditTestimonialEvent()
    data class OnTitleChange(val title: String) : EditTestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : EditTestimonialEvent()
    data class OnRoleChange(val role: String) : EditTestimonialEvent()
    data class OnOrgChange(val org: String) : EditTestimonialEvent()
    object OnSubmit : EditTestimonialEvent()
}