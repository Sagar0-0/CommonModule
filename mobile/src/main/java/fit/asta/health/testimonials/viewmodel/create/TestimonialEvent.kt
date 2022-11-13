package fit.asta.health.testimonials.viewmodel.create


sealed class TestimonialEvent {
    data class OnTypeChange(val type: Int) : TestimonialEvent()
    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    data class OnRoleChange(val role: String) : TestimonialEvent()
    data class OnOrgChange(val org: String) : TestimonialEvent()
    object OnSubmit : TestimonialEvent()
}