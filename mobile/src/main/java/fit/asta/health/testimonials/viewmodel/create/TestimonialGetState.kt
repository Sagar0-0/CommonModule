package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class TestimonialGetState {
    object Loading : TestimonialGetState()
    object Empty : TestimonialGetState()
    class Error(val error: Throwable) : TestimonialGetState()
    class Success(val testimonial: NetTestimonial) : TestimonialGetState()
}