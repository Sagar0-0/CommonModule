package fit.asta.health.testimonials.viewmodel

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class TestimonialState {
    object Loading : TestimonialState()
    class Success(val testimonial: NetTestimonial) : TestimonialState()
    class Update(val status: Status) : TestimonialState()
    class Error(val error: Throwable) : TestimonialState()
}