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
    data class OnImageSelect(val url: Uri?) : TestimonialEvent()
    data class OnImageClear(val inx: Int) : TestimonialEvent()
    data class OnVideoSelect(val url: Uri?) : TestimonialEvent()
    data class OnVideoClear(val inx: Int) : TestimonialEvent()
    object OnSubmit : TestimonialEvent()
}