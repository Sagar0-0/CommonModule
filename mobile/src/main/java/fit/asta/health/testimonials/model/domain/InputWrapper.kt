package fit.asta.health.testimonials.model.domain

import android.os.Parcelable
import fit.asta.health.common.utils.UiInt
import fit.asta.health.common.utils.UiString
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InputWrapper(val value: String = "", val error: UiString = UiString.Empty) :
    Parcelable  //for fields only

@Parcelize
data class InputIntWrapper(val value: Int = 0, val error: UiInt = UiInt.Empty) : Parcelable
