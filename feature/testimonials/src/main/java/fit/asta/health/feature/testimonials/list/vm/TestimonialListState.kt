package fit.asta.health.feature.testimonials.list.vm

sealed class TestimonialListState {
    object Loading : TestimonialListState()
    class Success(val testimonials: List<fit.asta.health.data.testimonials.model.Testimonial>) :
        TestimonialListState()

    class Error(val error: Throwable) : TestimonialListState()
}