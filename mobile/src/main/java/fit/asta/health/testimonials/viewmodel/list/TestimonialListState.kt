package fit.asta.health.testimonials.viewmodel.list

import fit.asta.health.testimonials.model.network.NetTestimonial


sealed class TestimonialListState {
    object Loading : TestimonialListState()
    class Success(val testimonials: List<NetTestimonial>) : TestimonialListState()
    class Error(val error: Throwable) : TestimonialListState()
}