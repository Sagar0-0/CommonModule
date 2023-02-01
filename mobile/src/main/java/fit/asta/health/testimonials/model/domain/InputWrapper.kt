package fit.asta.health.testimonials.model.domain

import android.net.Uri
import android.os.Parcelable
import fit.asta.health.utils.UiString
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InputWrapper(val value: String = "", val error: UiString = UiString.Empty) : Parcelable

@Parcelize
data class MediaWrapper(
    val localUrl: Uri? = null,
    val url: String = "",
    val error: UiString = UiString.Empty
) : Parcelable