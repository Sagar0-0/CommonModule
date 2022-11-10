package fit.asta.health.testimonials.viewmodel

import fit.asta.health.network.data.Status

sealed class TestimonialSubmitState {
    object Loading : GetTestimonialState()
    class Success(val status: Status) : TestimonialSubmitState()
    class Error(val error: Throwable) : TestimonialSubmitState()
}