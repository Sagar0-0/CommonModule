package fit.asta.health.testimonials.ui.list.vm


sealed class TestimonialListEvent {
    data class Remove(val id: String) : TestimonialListEvent()
}