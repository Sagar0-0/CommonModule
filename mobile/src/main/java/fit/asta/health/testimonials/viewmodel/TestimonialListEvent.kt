package fit.asta.health.testimonials.viewmodel


sealed class TestimonialListEvent {
    data class OnNextPage(val limit: Int, val index: Int) : TestimonialListEvent()
}