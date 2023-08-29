package fit.asta.health.feature.testimonials.list.vm

import fit.asta.health.data.testimonials.model.Testimonial

sealed class TestimonialListState {
    object Loading : TestimonialListState()
    class Success(val testimonials: List<Testimonial>) :
        TestimonialListState()

    class Error(val error: Throwable) : TestimonialListState()
}