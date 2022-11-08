package fit.asta.health.testimonials.viewmodel

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class EditTestimonialState {
    object Loading : EditTestimonialState()
    class Success(val testimonial: NetTestimonial) : EditTestimonialState()
    class Update(val status: Status) : EditTestimonialState()
    class Error(val error: Throwable) : EditTestimonialState()
}