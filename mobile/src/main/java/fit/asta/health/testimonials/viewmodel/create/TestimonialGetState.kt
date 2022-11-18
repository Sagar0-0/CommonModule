package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.testimonials.model.domain.Testimonial


sealed class TestimonialGetState {
    object Loading : TestimonialGetState()
    object NoInternet : TestimonialGetState()
    class Error(val error: Throwable) : TestimonialGetState()
    class Success(val testimonial: Testimonial) : TestimonialGetState()
}