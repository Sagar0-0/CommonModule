package fit.asta.health.old_profile.data.userprofile


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Value(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("value")
    var value: String = ""
): Parcelable