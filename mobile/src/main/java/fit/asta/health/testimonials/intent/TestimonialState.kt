package fit.asta.health.testimonials.intent

import fit.asta.health.testimonials.model.domain.Testimonial


sealed class TestimonialState {
    object Loading : TestimonialState()
    class Success(val testimonial: Testimonial) : TestimonialState()
    class Error(val error: Throwable) : TestimonialState()
}