package fit.asta.health.testimonials.viewmodel.create

import fit.asta.health.network.data.Status

sealed class TestimonialSubmitState {
    object Loading : TestimonialSubmitState()
    class Error(val error: Throwable) : TestimonialSubmitState()
    class Success(val status: Status) : TestimonialSubmitState()
}