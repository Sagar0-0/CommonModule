package fit.asta.health.testimonials.viewmodel

import fit.asta.health.testimonials.model.domain.Testimonial


sealed class TestimonialState {
    object Loading : TestimonialState()
    class Success(val testimonial: List<Testimonial>) : TestimonialState()
    class Error(val error: Throwable) : TestimonialState()
}