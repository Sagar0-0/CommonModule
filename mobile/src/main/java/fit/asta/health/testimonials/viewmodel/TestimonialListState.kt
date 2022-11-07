package fit.asta.health.testimonials.viewmodel

import fit.asta.health.testimonials.model.domain.Testimonial


sealed class TestimonialListState {
    object Loading : TestimonialListState()
    class Success(val testimonial: List<Testimonial>) : TestimonialListState()
    class Error(val error: Throwable) : TestimonialListState()
}