package fit.asta.health.data.profile.remote.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class BasicProfileDTO(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("mailUrl") val gmailPic: String? = null,
    val imageLocalUri: Uri? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("age") val age: Int = 0,
    @SerializedName("dob") val dob: String = "",
    @SerializedName("gen") val gen: Int = GenderCode.Other.gender,
    @SerializedName("mail") val mail: String = "",
    @SerializedName("ph") val ph: String = "",
    @SerializedName("rflCode") val refCode: String = "",
)

sealed class GenderCode(val gender: Int) {
    data object Male : GenderCode(1)
    data object Female : GenderCode(2)
    data object Other : GenderCode(3)
}