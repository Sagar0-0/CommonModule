package fit.asta.health.testimonials.viewmodel


sealed class EditTestimonialEvent {
    data class OnTitleChange(val title: String) : EditTestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : EditTestimonialEvent()
    object OnSaveClick : EditTestimonialEvent()
}