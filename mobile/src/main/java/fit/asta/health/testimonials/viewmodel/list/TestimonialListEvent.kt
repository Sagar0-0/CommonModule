package fit.asta.health.testimonials.viewmodel.list


sealed class TestimonialListEvent {
    data class Remove(val id: String) : TestimonialListEvent()
}