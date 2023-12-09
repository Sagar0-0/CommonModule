package fit.asta.health.feature.testimonials.events

import android.net.Uri
import fit.asta.health.data.testimonials.model.TestimonialType

sealed class MediaType {
    data object BeforeImage : MediaType()
    data object AfterImage : MediaType()
    data object Video : MediaType()
}

sealed class TestimonialEvent {

    data object GetUserTestimonial : TestimonialEvent()
    data class OnTypeChange(val type: TestimonialType) : TestimonialEvent()
    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    data class OnRoleChange(val role: String) : TestimonialEvent()
    data class OnOrgChange(val org: String) : TestimonialEvent()
    data class OnMediaSelect(val mediaType: MediaType, val url: Uri?) : TestimonialEvent()
    data class OnMediaClear(val mediaType: MediaType) : TestimonialEvent()
    data object OnSubmitTestimonial : TestimonialEvent()
}