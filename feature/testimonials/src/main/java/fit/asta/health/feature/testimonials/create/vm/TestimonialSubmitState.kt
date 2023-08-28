package fit.asta.health.feature.testimonials.create.vm

import fit.asta.health.network.data.Status
import java.io.IOException


sealed class TestimonialSubmitState {
    object Loading : TestimonialSubmitState()
    class Error(val error: Throwable) : TestimonialSubmitState()
    class NetworkError(val error: IOException) : TestimonialSubmitState()
    class Success(val status: Status) : TestimonialSubmitState()
}