package fit.asta.health.feature.testimonials.list.vm


sealed class TestimonialListEvent {
    data class Remove(val id: String) : TestimonialListEvent()
}