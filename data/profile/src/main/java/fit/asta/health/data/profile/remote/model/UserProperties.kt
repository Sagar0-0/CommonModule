package fit.asta.health.data.profile.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProperties(
    @SerializedName("id") val id: String = "",
    @SerializedName("type") val type: Int = 0,
    @SerializedName("code") val code: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("dsc") val description: String = "",
) : Parcelable