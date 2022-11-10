package fit.asta.health.testimonials.viewmodel.edit

import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class GetTestimonialState {
    object Loading : GetTestimonialState()
    class Success(val testimonial: NetTestimonial) : GetTestimonialState()
    class Error(val error: Throwable) : GetTestimonialState()
}