package fit.asta.health.testimonials.viewmodel


sealed class TestimonialEvent {
    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    object OnSaveClick : TestimonialEvent()
}