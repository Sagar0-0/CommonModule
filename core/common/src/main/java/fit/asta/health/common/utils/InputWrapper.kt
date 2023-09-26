package fit.asta.health.common.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InputWrapper(var value: String = "", val error: UiString = UiString.Empty) :
    Parcelable  //for fields only

@Parcelize
data class InputIntWrapper(val value: Int = 0, val error: UiInt = UiInt.Empty) : Parcelable
