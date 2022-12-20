package fit.asta.health.testimonials.viewmodel.create

import android.net.Uri
import fit.asta.health.testimonials.model.domain.TestimonialType


sealed class TestimonialEvent {
    data class OnTypeChange(val type: TestimonialType) : TestimonialEvent()
    data class OnTitleChange(val title: String) : TestimonialEvent()
    data class OnTestimonialChange(val testimonial: String) : TestimonialEvent()
    data class OnRoleChange(val role: String) : TestimonialEvent()
    data class OnOrgChange(val org: String) : TestimonialEvent()
    data class OnMediaIndex(val inx: Int) : TestimonialEvent()
    data class OnMediaSelect(val url: Uri?) : TestimonialEvent()
    data class OnMediaClear(val inx: Int) : TestimonialEvent()
    object OnSubmit : TestimonialEvent()
}