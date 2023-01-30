package fit.asta.health.testimonials.model.domain

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import fit.asta.health.R
import fit.asta.health.utils.UiString
import kotlinx.android.parcel.Parcelize


data class TestimonialData(
    val id: String = "",
    var imgMedia: SnapshotStateList<Media> = mutableStateListOf(
        Media(name = "before", title = "Before Image"),
        Media(name = "after", title = "After Image")
    ),
    var vdoMedia: SnapshotStateList<Media> = mutableStateListOf(
        Media(name = "journey", title = "Health Transformation")
    ),
    var imgError: UiString = UiString.Resource(R.string.the_media_can_not_be_blank),
    var vdoError: UiString = UiString.Resource(R.string.the_media_can_not_be_blank)
)

@Parcelize
data class InputWrapper(val value: String = "", val error: UiString = UiString.Empty) : Parcelable