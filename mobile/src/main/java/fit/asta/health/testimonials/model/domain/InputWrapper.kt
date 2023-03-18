package fit.asta.health.testimonials.model.domain

import android.os.Parcelable
import fit.asta.health.common.utils.UiString
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InputWrapper(val value: String = "", val error: UiString = UiString.Empty) : Parcelable