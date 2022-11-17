package fit.asta.health.testimonials.viewmodel


sealed class TestimonialListEvent {
    data class Remove(val id: String) : TestimonialListEvent()
}