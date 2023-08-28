package fit.asta.health.data.profile.remote.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class BasicProfileDTO(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("mailUrl") val mailUrl: String? = null,
    val imageLocalUri: Uri? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("gen") val gen: Int = 0,
    @SerializedName("mail") val mail: String = "",
    @SerializedName("ph") val ph: String = "",
    @SerializedName("refCode") val refCode: String = "",
)
