package fit.asta.health.testimonials.ui.list.vm

import fit.asta.health.testimonials.data.model.Testimonial


sealed class TestimonialListState {
    object Loading : TestimonialListState()
    class Success(val testimonials: List<Testimonial>) : TestimonialListState()
    class Error(val error: Throwable) : TestimonialListState()
}