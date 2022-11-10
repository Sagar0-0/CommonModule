package fit.asta.health.testimonials.viewmodel.edit

import fit.asta.health.network.data.Status

sealed class TestimonialSubmitState {
    object Loading : TestimonialSubmitState()
    class Success(val status: Status) : TestimonialSubmitState()
    class Error(val error: Throwable) : TestimonialSubmitState()
}