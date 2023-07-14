package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.testimonials.model.domain.Testimonial
import java.io.IOException


sealed class TestimonialGetState {
    object Loading : TestimonialGetState()
    object Empty : TestimonialGetState()
    class Error(val error: Throwable) : TestimonialGetState()
    class NetworkError(val error: IOException) : TestimonialGetState()
    class Success(val testimonial: Testimonial) : TestimonialGetState()
}