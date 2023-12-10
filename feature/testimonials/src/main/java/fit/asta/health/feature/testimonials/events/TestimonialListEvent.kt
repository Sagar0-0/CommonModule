package fit.asta.health.feature.testimonials.events


sealed class TestimonialListEvent {
    data class Remove(val id: String) : TestimonialListEvent()
}