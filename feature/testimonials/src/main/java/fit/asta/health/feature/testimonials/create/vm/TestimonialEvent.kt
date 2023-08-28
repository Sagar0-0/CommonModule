package fit.asta.health.feature.testimonials.create.vm

import android.net.Uri

sealed class MediaType {
    object BeforeImage: MediaType()
    object AfterImage: MediaType()
    object Video: MediaType()
}

sealed class TestimonialEvent {
    data class OnTypeChange(val type: fit.asta.health.data.testimonials.model.TestimonialType) :
        TestimonialEvent()

    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    data class OnRoleChange(val role: String) : TestimonialEvent()
    data class OnOrgChange(val org: String) : TestimonialEvent()
    data class OnMediaSelect(val mediaType: MediaType, val url: Uri?) : TestimonialEvent()
    data class OnMediaClear(val mediaType: MediaType) : TestimonialEvent()
    object OnSubmit : TestimonialEvent()
}