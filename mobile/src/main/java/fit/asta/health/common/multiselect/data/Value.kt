package fit.asta.health.common.multiselect.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Value(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("val")
    val value: String = ""
) : Parcelable