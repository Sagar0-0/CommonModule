package fit.asta.health.testimonials.viewmodel.edit


sealed class EditTestimonialEvent {
    data class OnTypeChange(val type: Int) : EditTestimonialEvent()
    data class OnTitleChange(val title: String) : EditTestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : EditTestimonialEvent()
    data class OnRoleChange(val role: String) : EditTestimonialEvent()
    data class OnOrgChange(val org: String) : EditTestimonialEvent()
    object OnSubmit : EditTestimonialEvent()
}