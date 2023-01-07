package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import fit.asta.health.testimonials.model.domain.Media
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.utils.UiString


data class TestimonialData(
    val id: String = "",
    val type: TestimonialType = TestimonialType.TEXT,
    val title: String = "",
    var titleError: UiString = UiString.Empty,
    val testimonial: String = "",
    var testimonialError: UiString = UiString.Empty,
    val role: String = "",
    var roleError: UiString = UiString.Empty,
    val org: String = "",
    var orgError: UiString = UiString.Empty,
    var imgMedia: SnapshotStateList<Media> = mutableStateListOf(
        Media(name = "before", title = "Before Image"),
        Media(name = "after", title = "After Image")
    ),
    var vdoMedia: SnapshotStateList<Media> = mutableStateListOf(
        Media(name = "journey", title = "Health Transformation")
    ),
    var mediaError: UiString = UiString.Empty,
    var enableSubmit: Boolean = false
)