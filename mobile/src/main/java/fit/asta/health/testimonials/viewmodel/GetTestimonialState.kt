package fit.asta.health.testimonials.viewmodel

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class GetTestimonialState {
    object Loading : GetTestimonialState()
    class Success(val testimonial: NetTestimonial) : GetTestimonialState()
    class Update(val status: Status) : GetTestimonialState()
    class Error(val error: Throwable) : GetTestimonialState()
}