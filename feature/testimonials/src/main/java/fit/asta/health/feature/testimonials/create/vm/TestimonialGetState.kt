package fit.asta.health.feature.testimonials.create.vm

import fit.asta.health.data.testimonials.model.Testimonial
import java.io.IOException


sealed class TestimonialGetState {
    object Loading : TestimonialGetState()
    object Empty : TestimonialGetState()
    class Error(val error: Throwable) : TestimonialGetState()
    class NetworkError(val error: IOException) : TestimonialGetState()
    class Success(val testimonial: Testimonial) :
        TestimonialGetState()
}